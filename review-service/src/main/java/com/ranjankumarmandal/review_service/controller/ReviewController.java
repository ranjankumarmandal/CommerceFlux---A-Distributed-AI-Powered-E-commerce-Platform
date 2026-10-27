package com.ranjankumarmandal.review_service.controller;

import com.ranjankumarmandal.review_service.dto.request.ReviewRequest;
import com.ranjankumarmandal.review_service.dto.response.ApiResponse;
import com.ranjankumarmandal.review_service.dto.response.ReviewResponse;
import com.ranjankumarmandal.review_service.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<ReviewResponse>> create(@RequestBody ReviewRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(reviewService.create(request), "Review created successfully")
		);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<ReviewResponse>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(
				ApiResponse.ok(reviewService.getById(id), "Review fetched successfully")
		);
	}

	@GetMapping("/product/{productId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<ReviewResponse>>> getByProductId(@PathVariable Long productId) {
		return ResponseEntity.ok(
				ApiResponse.ok(reviewService.getByProductId(productId), "Reviews fetched successfully")
		);
	}

	@GetMapping("/customer/{customerId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<ReviewResponse>>> getByCustomerId(@PathVariable Long customerId) {
		return ResponseEntity.ok(
				ApiResponse.ok(reviewService.getByCustomerId(customerId), "Reviews fetched successfully")
		);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		reviewService.delete(id);
		return ResponseEntity.ok(
				ApiResponse.ok(null, "Review deleted successfully")
		);
	}
}
