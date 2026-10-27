package com.ranjankumarmandal.inventory_service.service;

import com.ranjankumarmandal.inventory_service.dto.request.InventoryRequest;
import com.ranjankumarmandal.inventory_service.dto.request.StockAdjustmentRequest;
import com.ranjankumarmandal.inventory_service.dto.response.InventoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryService {
	InventoryResponse create(InventoryRequest request);
	InventoryResponse getById(Long id);
	InventoryResponse getByProductId(Long productId);
	List<InventoryResponse> getAll();
	Page<InventoryResponse> getAll(Pageable pageable);
	InventoryResponse update(Long id, InventoryRequest request);
	void delete(Long id);
	InventoryResponse addStock(Long productId, Integer quantity);
	InventoryResponse reserveStock(Long productId, Integer quantity);
	InventoryResponse releaseReservedStock(Long productId, Integer quantity);
	InventoryResponse consumeReservedStock(Long productId, Integer quantity);
}
