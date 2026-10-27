package com.ranjankumarmandal.payment_service.controller;

import com.ranjankumarmandal.payment_service.dto.request.PaymentRequest;
import com.ranjankumarmandal.payment_service.dto.response.ApiResponse;
import com.ranjankumarmandal.payment_service.dto.response.PaymentResponse;
import com.ranjankumarmandal.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PaymentResponse>> create(@RequestBody PaymentRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(paymentService.create(request), "Payment processed successfully")
		);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PaymentResponse>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(
				ApiResponse.ok(paymentService.getById(id), "Payment fetched successfully")
		);
	}

	@GetMapping("/transaction/{transactionId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PaymentResponse>> getByTransactionId(@PathVariable String transactionId) {
		return ResponseEntity.ok(
				ApiResponse.ok(paymentService.getByTransactionId(transactionId), "Payment fetched successfully")
		);
	}

	@GetMapping("/order/{orderId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PaymentResponse>> getByOrderId(@PathVariable Long orderId) {
		return ResponseEntity.ok(
				ApiResponse.ok(paymentService.getByOrderId(orderId), "Payment fetched successfully")
		);
	}

	@PostMapping("/{id}/refund")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<PaymentResponse>> refund(@PathVariable Long id) {
		return ResponseEntity.ok(
				ApiResponse.ok(paymentService.refund(id), "Payment refunded successfully")
		);
	}
}
