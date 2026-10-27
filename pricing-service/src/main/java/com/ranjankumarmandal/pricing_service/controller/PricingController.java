package com.ranjankumarmandal.pricing_service.controller;

import com.ranjankumarmandal.pricing_service.dto.request.PriceRequest;
import com.ranjankumarmandal.pricing_service.dto.response.ApiResponse;
import com.ranjankumarmandal.pricing_service.dto.response.PriceResponse;
import com.ranjankumarmandal.pricing_service.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/pricing")
@RequiredArgsConstructor
public class PricingController {

	private final PricingService pricingService;

	@PostMapping
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PriceResponse>> create(@RequestBody PriceRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(pricingService.create(request), "Price created successfully")
		);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PriceResponse>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(
				ApiResponse.ok(pricingService.getById(id), "Price fetched successfully")
		);
	}

	@GetMapping("/product/{productId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PriceResponse>> getByProductId(@PathVariable Long productId) {
		return ResponseEntity.ok(
				ApiResponse.ok(pricingService.getByProductId(productId), "Price fetched successfully")
		);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PriceResponse>> update(
			@PathVariable Long id,
			@RequestBody PriceRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(pricingService.update(id, request), "Price updated successfully")
		);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		pricingService.delete(id);
		return ResponseEntity.ok(
				ApiResponse.ok(null, "Price deleted successfully")
		);
	}

	@PostMapping("/apply-discount/{productId}")
	@PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PriceResponse>> applyDiscount(
			@PathVariable Long productId,
			@RequestParam BigDecimal discountPercentage) {
		return ResponseEntity.ok(
				ApiResponse.ok(pricingService.applyDiscount(productId, discountPercentage), "Discount applied successfully")
		);
	}

	@PostMapping("/dynamic-price/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PriceResponse>> calculateDynamicPrice(@PathVariable Long productId) {
		return ResponseEntity.ok(
				ApiResponse.ok(pricingService.calculateDynamicPrice(productId), "Dynamic price calculated successfully")
		);
	}
}
