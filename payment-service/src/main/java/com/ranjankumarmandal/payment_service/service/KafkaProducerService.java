package com.ranjankumarmandal.payment_service.service;

import com.ranjankumarmandal.payment_service.dto.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendPaymentEvent(String topic, PaymentEvent event) {
		event.setTimestamp(LocalDateTime.now());
		CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event.getTransactionId(), event);
		
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				log.info("Payment event sent successfully: {} to partition {}", event, result.getRecordMetadata().partition());
			} else {
				log.error("Failed to send payment event: {}", event, ex);
			}
		});
	}

	public void sendPaymentCompletedEvent(Long paymentId, String transactionId, Long orderId, Long customerId, Object payload) {
		PaymentEvent event = PaymentEvent.builder()
				.paymentId(paymentId)
				.transactionId(transactionId)
				.orderId(orderId)
				.customerId(customerId)
				.eventType("PAYMENT_COMPLETED")
				.payload(payload)
				.build();
		sendPaymentEvent("payment-events", event);
	}

	public void sendPaymentFailedEvent(Long paymentId, String transactionId, Long orderId, Long customerId, Object payload) {
		PaymentEvent event = PaymentEvent.builder()
				.paymentId(paymentId)
				.transactionId(transactionId)
				.orderId(orderId)
				.customerId(customerId)
				.eventType("PAYMENT_FAILED")
				.payload(payload)
				.build();
		sendPaymentEvent("payment-events", event);
	}

	public void sendPaymentRefundedEvent(Long paymentId, String transactionId, Long orderId, Long customerId) {
		PaymentEvent event = PaymentEvent.builder()
				.paymentId(paymentId)
				.transactionId(transactionId)
				.orderId(orderId)
				.customerId(customerId)
				.eventType("PAYMENT_REFUNDED")
				.build();
		sendPaymentEvent("payment-events", event);
	}
}
