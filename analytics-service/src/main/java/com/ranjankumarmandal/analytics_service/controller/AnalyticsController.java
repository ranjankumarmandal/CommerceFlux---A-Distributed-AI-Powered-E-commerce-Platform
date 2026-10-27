package com.ranjankumarmandal.analytics_service.controller;

import com.ranjankumarmandal.analytics_service.dto.response.ApiResponse;
import com.ranjankumarmandal.analytics_service.dto.response.AnalyticsResponse;
import com.ranjankumarmandal.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

	private final AnalyticsService analyticsService;

	@GetMapping("/sales")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
	public ResponseEntity<ApiResponse<AnalyticsResponse>> getSalesAnalytics() {
		return ResponseEntity.ok(
				ApiResponse.ok(analyticsService.getSalesAnalytics(), "Sales analytics fetched successfully")
		);
	}

	@GetMapping("/customers")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
	public ResponseEntity<ApiResponse<AnalyticsResponse>> getCustomerAnalytics() {
		return ResponseEntity.ok(
				ApiResponse.ok(analyticsService.getCustomerAnalytics(), "Customer analytics fetched successfully")
		);
	}

	@GetMapping("/products")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
	public ResponseEntity<ApiResponse<AnalyticsResponse>> getProductAnalytics() {
		return ResponseEntity.ok(
				ApiResponse.ok(analyticsService.getProductAnalytics(), "Product analytics fetched successfully")
		);
	}

	@GetMapping("/realtime")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
	public ResponseEntity<ApiResponse<Map<String, Object>>> getRealtimeMetrics() {
		return ResponseEntity.ok(
				ApiResponse.ok(analyticsService.getRealtimeMetrics(), "Realtime metrics fetched successfully")
		);
	}
}
