package com.ranjankumarmandal.inventory_service.controller;

import com.ranjankumarmandal.inventory_service.dto.request.InventoryRequest;
import com.ranjankumarmandal.inventory_service.dto.request.StockAdjustmentRequest;
import com.ranjankumarmandal.inventory_service.dto.response.ApiResponse;
import com.ranjankumarmandal.inventory_service.dto.response.InventoryResponse;
import com.ranjankumarmandal.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@PostMapping
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<InventoryResponse>> create(@RequestBody InventoryRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.create(request), "Inventory created successfully")
		);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<InventoryResponse>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.getById(id), "Inventory fetched successfully")
		);
	}

	@GetMapping("/product/{productId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<InventoryResponse>> getByProductId(@PathVariable Long productId) {
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.getByProductId(productId), "Inventory fetched successfully")
		);
	}

	@GetMapping
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAll() {
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.getAll(), "Inventories fetched successfully")
		);
	}

	@GetMapping("/paginated")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Page<InventoryResponse>>> getAllPaginated(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.getAll(pageable), "Inventories fetched successfully")
		);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<InventoryResponse>> update(
			@PathVariable Long id,
			@RequestBody InventoryRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.update(id, request), "Inventory updated successfully")
		);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		inventoryService.delete(id);
		return ResponseEntity.ok(
				ApiResponse.ok(null, "Inventory deleted successfully")
		);
	}

	@PostMapping("/add-stock")
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<InventoryResponse>> addStock(@RequestBody StockAdjustmentRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.addStock(request.getProductId(), request.getQuantity()), "Stock added successfully")
		);
	}

	@PostMapping("/reserve-stock")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<InventoryResponse>> reserveStock(@RequestBody StockAdjustmentRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.reserveStock(request.getProductId(), request.getQuantity()), "Stock reserved successfully")
		);
	}

	@PostMapping("/release-reserved-stock")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<InventoryResponse>> releaseReservedStock(@RequestBody StockAdjustmentRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.releaseReservedStock(request.getProductId(), request.getQuantity()), "Reserved stock released successfully")
		);
	}

	@PostMapping("/consume-reserved-stock")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<InventoryResponse>> consumeReservedStock(@RequestBody StockAdjustmentRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(inventoryService.consumeReservedStock(request.getProductId(), request.getQuantity()), "Reserved stock consumed successfully")
		);
	}
}
