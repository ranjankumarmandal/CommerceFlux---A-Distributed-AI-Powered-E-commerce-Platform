package com.ranjankumarmandal.order_service.controller;

import com.ranjankumarmandal.order_service.dto.request.OrderRequest;
import com.ranjankumarmandal.order_service.dto.response.ApiResponse;
import com.ranjankumarmandal.order_service.dto.response.OrderResponse;
import com.ranjankumarmandal.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<OrderResponse>> create(@RequestBody OrderRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(orderService.create(request), "Order created successfully")
		);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<OrderResponse>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(
				ApiResponse.ok(orderService.getById(id), "Order fetched successfully")
		);
	}

	@GetMapping("/order-number/{orderNumber}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<OrderResponse>> getByOrderNumber(@PathVariable String orderNumber) {
		return ResponseEntity.ok(
				ApiResponse.ok(orderService.getByOrderNumber(orderNumber), "Order fetched successfully")
		);
	}

	@GetMapping("/customer/{customerId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<OrderResponse>>> getByCustomerId(@PathVariable Long customerId) {
		return ResponseEntity.ok(
				ApiResponse.ok(orderService.getByCustomerId(customerId), "Orders fetched successfully")
		);
	}

	@PutMapping("/{id}/status")
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<OrderResponse>> updateStatus(
			@PathVariable Long id,
			@RequestParam String status) {
		return ResponseEntity.ok(
				ApiResponse.ok(orderService.updateStatus(id, status), "Order status updated successfully")
		);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		orderService.delete(id);
		return ResponseEntity.ok(
				ApiResponse.ok(null, "Order deleted successfully")
		);
	}
}
