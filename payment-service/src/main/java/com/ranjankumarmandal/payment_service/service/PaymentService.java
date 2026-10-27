package com.ranjankumarmandal.payment_service.service;

import com.ranjankumarmandal.payment_service.dto.request.PaymentRequest;
import com.ranjankumarmandal.payment_service.dto.response.PaymentResponse;

public interface PaymentService {
	PaymentResponse create(PaymentRequest request);
	PaymentResponse getById(Long id);
	PaymentResponse getByTransactionId(String transactionId);
	PaymentResponse getByOrderId(Long orderId);
	PaymentResponse refund(Long id);
}
