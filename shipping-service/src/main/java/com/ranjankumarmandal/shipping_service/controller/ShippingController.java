package com.ranjankumarmandal.shipping_service.controller;

import com.ranjankumarmandal.shipping_service.dto.request.ShipmentRequest;
import com.ranjankumarmandal.shipping_service.dto.response.ApiResponse;
import com.ranjankumarmandal.shipping_service.dto.response.ShipmentResponse;
import com.ranjankumarmandal.shipping_service.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipping")
@RequiredArgsConstructor
public class ShippingController {

	private final ShippingService shippingService;

	@PostMapping
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<ShipmentResponse>> create(@RequestBody ShipmentRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(shippingService.create(request), "Shipment created successfully")
		);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<ShipmentResponse>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(
				ApiResponse.ok(shippingService.getById(id), "Shipment fetched successfully")
		);
	}

	@GetMapping("/tracking/{trackingNumber}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<ShipmentResponse>> getByTrackingNumber(@PathVariable String trackingNumber) {
		return ResponseEntity.ok(
				ApiResponse.ok(shippingService.getByTrackingNumber(trackingNumber), "Shipment fetched successfully")
		);
	}

	@GetMapping("/order/{orderId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<ShipmentResponse>> getByOrderId(@PathVariable Long orderId) {
		return ResponseEntity.ok(
				ApiResponse.ok(shippingService.getByOrderId(orderId), "Shipment fetched successfully")
		);
	}

	@PutMapping("/{id}/status")
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<ShipmentResponse>> updateStatus(
			@PathVariable Long id,
			@RequestParam String status) {
		return ResponseEntity.ok(
				ApiResponse.ok(shippingService.updateStatus(id, status), "Shipment status updated successfully")
		);
	}
}
