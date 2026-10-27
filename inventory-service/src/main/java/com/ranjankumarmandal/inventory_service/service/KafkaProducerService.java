package com.ranjankumarmandal.inventory_service.service;

import com.ranjankumarmandal.inventory_service.dto.event.InventoryEvent;
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

	public void sendInventoryEvent(String topic, InventoryEvent event) {
		event.setTimestamp(LocalDateTime.now());
		CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event.getProductId().toString(), event);
		
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				log.info("Inventory event sent successfully: {} to partition {}", event, result.getRecordMetadata().partition());
			} else {
				log.error("Failed to send inventory event: {}", event, ex);
			}
		});
	}

	public void sendInventoryCreatedEvent(Long productId, String productName, Object payload) {
		InventoryEvent event = InventoryEvent.builder()
				.productId(productId)
				.productName(productName)
				.eventType("INVENTORY_CREATED")
				.payload(payload)
				.build();
		sendInventoryEvent("inventory-events", event);
	}

	public void sendStockUpdatedEvent(Long productId, String productName, Object payload) {
		InventoryEvent event = InventoryEvent.builder()
				.productId(productId)
				.productName(productName)
				.eventType("STOCK_UPDATED")
				.payload(payload)
				.build();
		sendInventoryEvent("inventory-events", event);
	}

	public void sendLowStockEvent(Long productId, String productName, Object payload) {
		InventoryEvent event = InventoryEvent.builder()
				.productId(productId)
				.productName(productName)
				.eventType("LOW_STOCK")
				.payload(payload)
				.build();
		sendInventoryEvent("inventory-events", event);
	}

	public void sendOutOfStockEvent(Long productId, String productName) {
		InventoryEvent event = InventoryEvent.builder()
				.productId(productId)
				.productName(productName)
				.eventType("OUT_OF_STOCK")
				.build();
		sendInventoryEvent("inventory-events", event);
	}
}
