package com.ranjankumarmandal.cart_service.service;

import com.ranjankumarmandal.cart_service.dto.event.CartEvent;
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

	public void sendCartEvent(String topic, CartEvent event) {
		event.setTimestamp(LocalDateTime.now());
		CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event.getCustomerId(), event);
		
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				log.info("Cart event sent successfully: {} to partition {}", event, result.getRecordMetadata().partition());
			} else {
				log.error("Failed to send cart event: {}", event, ex);
			}
		});
	}

	public void sendCartItemAddedEvent(String customerId, Object payload) {
		CartEvent event = CartEvent.builder()
				.customerId(customerId)
				.eventType("CART_ITEM_ADDED")
				.payload(payload)
				.build();
		sendCartEvent("cart-events", event);
	}

	public void sendCartItemRemovedEvent(String customerId, Object payload) {
		CartEvent event = CartEvent.builder()
				.customerId(customerId)
				.eventType("CART_ITEM_REMOVED")
				.payload(payload)
				.build();
		sendCartEvent("cart-events", event);
	}

	public void sendCartClearedEvent(String customerId) {
		CartEvent event = CartEvent.builder()
				.customerId(customerId)
				.eventType("CART_CLEARED")
				.build();
		sendCartEvent("cart-events", event);
	}
}
