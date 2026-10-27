package com.ranjankumarmandal.inventory_service.service.impl;

import com.ranjankumarmandal.inventory_service.dto.request.InventoryRequest;
import com.ranjankumarmandal.inventory_service.dto.response.InventoryResponse;
import com.ranjankumarmandal.inventory_service.entity.Inventory;
import com.ranjankumarmandal.inventory_service.enums.InventoryStatus;
import com.ranjankumarmandal.inventory_service.exception.InsufficientStockException;
import com.ranjankumarmandal.inventory_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.inventory_service.repository.InventoryRepository;
import com.ranjankumarmandal.inventory_service.service.InventoryService;
import com.ranjankumarmandal.inventory_service.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepository;
	private final KafkaProducerService kafkaProducerService;

	@Override
	@CacheEvict(value = "inventories", allEntries = true)
	public InventoryResponse create(InventoryRequest request) {
		if (inventoryRepository.existsByProductId(request.getProductId())) {
			throw new ResourceNotFoundException("Inventory already exists for product");
		}

		Inventory inventory = Inventory.builder()
				.productId(request.getProductId())
				.quantity(request.getQuantity())
				.reservedQuantity(0)
				.reorderThreshold(request.getReorderThreshold())
				.reorderLevel(request.getReorderLevel())
				.status(determineStatus(request.getQuantity(), request.getReorderThreshold()))
				.warehouseLocation(request.getWarehouseLocation())
				.supplierId(request.getSupplierId())
				.build();

		inventory = inventoryRepository.save(inventory);

		InventoryResponse response = map(inventory);
		kafkaProducerService.sendInventoryCreatedEvent(inventory.getProductId(), "Product " + inventory.getProductId(), response);

		return response;
	}

	@Override
	@Cacheable(value = "inventories", key = "#id")
	public InventoryResponse getById(Long id) {
		Inventory inventory = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
		return map(inventory);
	}

	@Override
	@Cacheable(value = "inventories", key = "'product:' + #productId")
	public InventoryResponse getByProductId(Long productId) {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product"));
		return map(inventory);
	}

	@Override
	public List<InventoryResponse> getAll() {
		return inventoryRepository.findAll().stream()
				.map(this::map)
				.toList();
	}

	@Override
	public Page<InventoryResponse> getAll(Pageable pageable) {
		return inventoryRepository.findAll(pageable)
				.map(this::map);
	}

	@Override
	@CachePut(value = "inventories", key = "#id")
	public InventoryResponse update(Long id, InventoryRequest request) {
		Inventory inventory = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

		inventory.setQuantity(request.getQuantity());
		inventory.setReorderThreshold(request.getReorderThreshold());
		inventory.setReorderLevel(request.getReorderLevel());
		inventory.setWarehouseLocation(request.getWarehouseLocation());
		inventory.setSupplierId(request.getSupplierId());
		inventory.setStatus(determineStatus(request.getQuantity(), request.getReorderThreshold()));

		InventoryResponse response = map(inventoryRepository.save(inventory));
		kafkaProducerService.sendStockUpdatedEvent(inventory.getProductId(), "Product " + inventory.getProductId(), response);

		return response;
	}

	@Override
	@CacheEvict(value = "inventories", key = "#id")
	public void delete(Long id) {
		Inventory inventory = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
		inventoryRepository.delete(inventory);
	}

	@Override
	@Transactional
	@CachePut(value = "inventories", key = "'product:' + #productId")
	public InventoryResponse addStock(Long productId, Integer quantity) {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product"));

		inventory.setQuantity(inventory.getQuantity() + quantity);
		inventory.setStatus(determineStatus(inventory.getQuantity(), inventory.getReorderThreshold()));

		InventoryResponse response = map(inventoryRepository.save(inventory));
		kafkaProducerService.sendStockUpdatedEvent(productId, "Product " + productId, response);

		return response;
	}

	@Override
	@Transactional
	@CachePut(value = "inventories", key = "'product:' + #productId")
	public InventoryResponse reserveStock(Long productId, Integer quantity) {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product"));

		int availableQuantity = inventory.getQuantity() - inventory.getReservedQuantity();
		if (availableQuantity < quantity) {
			throw new InsufficientStockException("Insufficient stock available. Available: " + availableQuantity);
		}

		inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
		inventory.setStatus(determineStatus(inventory.getQuantity(), inventory.getReorderThreshold()));

		InventoryResponse response = map(inventoryRepository.save(inventory));
		kafkaProducerService.sendStockUpdatedEvent(productId, "Product " + productId, response);

		return response;
	}

	@Override
	@Transactional
	@CachePut(value = "inventories", key = "'product:' + #productId")
	public InventoryResponse releaseReservedStock(Long productId, Integer quantity) {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product"));

		if (inventory.getReservedQuantity() < quantity) {
			throw new InsufficientStockException("Insufficient reserved stock to release");
		}

		inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
		inventory.setStatus(determineStatus(inventory.getQuantity(), inventory.getReorderThreshold()));

		return map(inventoryRepository.save(inventory));
	}

	@Override
	@Transactional
	@CachePut(value = "inventories", key = "'product:' + #productId")
	public InventoryResponse consumeReservedStock(Long productId, Integer quantity) {
		Inventory inventory = inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product"));

		if (inventory.getReservedQuantity() < quantity) {
			throw new InsufficientStockException("Insufficient reserved stock to consume");
		}

		inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
		inventory.setQuantity(inventory.getQuantity() - quantity);
		inventory.setStatus(determineStatus(inventory.getQuantity(), inventory.getReorderThreshold()));

		InventoryResponse response = map(inventoryRepository.save(inventory));
		kafkaProducerService.sendStockUpdatedEvent(productId, "Product " + productId, response);

		if (inventory.getQuantity() <= inventory.getReorderThreshold()) {
			kafkaProducerService.sendLowStockEvent(productId, "Product " + productId, response);
		}

		if (inventory.getQuantity() == 0) {
			kafkaProducerService.sendOutOfStockEvent(productId, "Product " + productId);
		}

		return response;
	}

	private InventoryStatus determineStatus(int quantity, int reorderThreshold) {
		if (quantity == 0) {
			return InventoryStatus.OUT_OF_STOCK;
		} else if (quantity <= reorderThreshold) {
			return InventoryStatus.LOW_STOCK;
		}
		return InventoryStatus.AVAILABLE;
	}

	private InventoryResponse map(Inventory inventory) {
		return InventoryResponse.builder()
				.id(inventory.getId())
				.productId(inventory.getProductId())
				.quantity(inventory.getQuantity())
				.reservedQuantity(inventory.getReservedQuantity())
				.availableQuantity(inventory.getQuantity() - inventory.getReservedQuantity())
				.reorderThreshold(inventory.getReorderThreshold())
				.reorderLevel(inventory.getReorderLevel())
				.status(inventory.getStatus().name())
				.warehouseLocation(inventory.getWarehouseLocation())
				.supplierId(inventory.getSupplierId())
				.build();
	}
}
