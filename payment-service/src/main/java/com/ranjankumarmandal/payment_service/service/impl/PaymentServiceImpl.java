package com.ranjankumarmandal.payment_service.service.impl;

import com.ranjankumarmandal.payment_service.dto.request.PaymentRequest;
import com.ranjankumarmandal.payment_service.dto.response.PaymentResponse;
import com.ranjankumarmandal.payment_service.entity.Payment;
import com.ranjankumarmandal.payment_service.enums.PaymentStatus;
import com.ranjankumarmandal.payment_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.payment_service.repository.PaymentRepository;
import com.ranjankumarmandal.payment_service.service.KafkaProducerService;
import com.ranjankumarmandal.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final KafkaProducerService kafkaProducerService;

	@Override
	@Transactional
	public PaymentResponse create(PaymentRequest request) {
		String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();

		Payment payment = Payment.builder()
				.orderId(request.getOrderId())
				.transactionId(transactionId)
				.customerId(request.getCustomerId())
				.amount(request.getAmount())
				.currency(request.getCurrency())
				.status(PaymentStatus.PENDING)
				.paymentMethod(request.getPaymentMethod())
				.paymentGateway(request.getPaymentGateway())
				.build();

		payment = paymentRepository.save(payment);

		boolean paymentSuccess = processPayment(payment);
		payment.setStatus(paymentSuccess ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);
		payment.setGatewayResponse(paymentSuccess ? "Payment successful" : "Payment failed");
		payment = paymentRepository.save(payment);

		PaymentResponse response = map(payment);
		if (paymentSuccess) {
			kafkaProducerService.sendPaymentCompletedEvent(payment.getId(), payment.getTransactionId(), payment.getOrderId(), payment.getCustomerId(), response);
		} else {
			kafkaProducerService.sendPaymentFailedEvent(payment.getId(), payment.getTransactionId(), payment.getOrderId(), payment.getCustomerId(), response);
		}

		return response;
	}

	@Override
	public PaymentResponse getById(Long id) {
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
		return map(payment);
	}

	@Override
	public PaymentResponse getByTransactionId(String transactionId) {
		Payment payment = paymentRepository.findByTransactionId(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
		return map(payment);
	}

	@Override
	public PaymentResponse getByOrderId(Long orderId) {
		Payment payment = paymentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found for order"));
		return map(payment);
	}

	@Override
	@Transactional
	public PaymentResponse refund(Long id) {
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

		if (payment.getStatus() != PaymentStatus.COMPLETED) {
			throw new RuntimeException("Only completed payments can be refunded");
		}

		payment.setStatus(PaymentStatus.REFUNDED);
		payment = paymentRepository.save(payment);

		PaymentResponse response = map(payment);
		kafkaProducerService.sendPaymentRefundedEvent(payment.getId(), payment.getTransactionId(), payment.getOrderId(), payment.getCustomerId());

		return response;
	}

	private boolean processPayment(Payment payment) {
		return true;
	}

	private PaymentResponse map(Payment payment) {
		return PaymentResponse.builder()
				.id(payment.getId())
				.orderId(payment.getOrderId())
				.transactionId(payment.getTransactionId())
				.customerId(payment.getCustomerId())
				.amount(payment.getAmount())
				.currency(payment.getCurrency())
				.status(payment.getStatus().name())
				.paymentMethod(payment.getPaymentMethod())
				.paymentGateway(payment.getPaymentGateway())
				.gatewayResponse(payment.getGatewayResponse())
				.createdAt(payment.getCreatedAt())
				.build();
	}
}
