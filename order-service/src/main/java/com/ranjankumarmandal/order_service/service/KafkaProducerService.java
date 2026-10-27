package com.ranjankumarmandal.order_service.service;

import com.ranjankumarmandal.order_service.dto.event.OrderEvent;
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

	public void sendOrderEvent(String topic, OrderEvent event) {
		event.setTimestamp(LocalDateTime.now());
		CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event.getOrderNumber(), event);
		
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				log.info("Order event sent successfully: {} to partition {}", event, result.getRecordMetadata().partition());
			} else {
				log.error("Failed to send order event: {}", event, ex);
			}
		});
	}

	public void sendOrderCreatedEvent(Long orderId, String orderNumber, Long customerId, Object payload) {
		OrderEvent event = OrderEvent.builder()
				.orderId(orderId)
				.orderNumber(orderNumber)
				.customerId(customerId)
				.eventType("ORDER_CREATED")
				.payload(payload)
				.build();
		sendOrderEvent("order-events", event);
	}

	public void sendOrderStatusUpdatedEvent(Long orderId, String orderNumber, Long customerId, Object payload) {
		OrderEvent event = OrderEvent.builder()
				.orderId(orderId)
				.orderNumber(orderNumber)
				.customerId(customerId)
				.eventType("ORDER_STATUS_UPDATED")
				.payload(payload)
				.build();
		sendOrderEvent("order-events", event);
	}

	public void sendOrderCancelledEvent(Long orderId, String orderNumber, Long customerId) {
		OrderEvent event = OrderEvent.builder()
				.orderId(orderId)
				.orderNumber(orderNumber)
				.customerId(customerId)
				.eventType("ORDER_CANCELLED")
				.build();
		sendOrderEvent("order-events", event);
	}
}
