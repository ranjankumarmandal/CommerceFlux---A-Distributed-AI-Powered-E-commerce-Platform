package com.ranjankumarmandal.pricing_service.service;

import com.ranjankumarmandal.pricing_service.dto.event.PriceEvent;
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

	public void sendPriceEvent(String topic, PriceEvent event) {
		event.setTimestamp(LocalDateTime.now());
		CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event.getProductId().toString(), event);
		
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				log.info("Price event sent successfully: {} to partition {}", event, result.getRecordMetadata().partition());
			} else {
				log.error("Failed to send price event: {}", event, ex);
			}
		});
	}

	public void sendPriceCreatedEvent(Long productId, String productName, Object payload) {
		PriceEvent event = PriceEvent.builder()
				.productId(productId)
				.productName(productName)
				.eventType("PRICE_CREATED")
				.payload(payload)
				.build();
		sendPriceEvent("price-events", event);
	}

	public void sendPriceUpdatedEvent(Long productId, String productName, Object payload) {
		PriceEvent event = PriceEvent.builder()
				.productId(productId)
				.productName(productName)
				.eventType("PRICE_UPDATED")
				.payload(payload)
				.build();
		sendPriceEvent("price-events", event);
	}

	public void sendDiscountAppliedEvent(Long productId, String productName, Object payload) {
		PriceEvent event = PriceEvent.builder()
				.productId(productId)
				.productName(productName)
				.eventType("DISCOUNT_APPLIED")
				.payload(payload)
				.build();
		sendPriceEvent("price-events", event);
	}
}
