package com.ranjankumarmandal.catalog_service.service;

import com.ranjankumarmandal.catalog_service.dto.event.ProductEvent;
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

    public void sendProductEvent(String topic, ProductEvent event) {
        event.setTimestamp(LocalDateTime.now());
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event.getProductId().toString(), event);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Product event sent successfully: {} to partition {}", event, result.getRecordMetadata().partition());
            } else {
                log.error("Failed to send product event: {}", event, ex);
            }
        });
    }

    public void sendProductCreatedEvent(Long productId, String productName, Object payload) {
        ProductEvent event = ProductEvent.builder()
                .productId(productId)
                .productName(productName)
                .eventType("PRODUCT_CREATED")
                .payload(payload)
                .build();
        sendProductEvent("product-events", event);
    }

    public void sendProductUpdatedEvent(Long productId, String productName, Object payload) {
        ProductEvent event = ProductEvent.builder()
                .productId(productId)
                .productName(productName)
                .eventType("PRODUCT_UPDATED")
                .payload(payload)
                .build();
        sendProductEvent("product-events", event);
    }

    public void sendProductDeletedEvent(Long productId, String productName) {
        ProductEvent event = ProductEvent.builder()
                .productId(productId)
                .productName(productName)
                .eventType("PRODUCT_DELETED")
                .build();
        sendProductEvent("product-events", event);
    }
}
