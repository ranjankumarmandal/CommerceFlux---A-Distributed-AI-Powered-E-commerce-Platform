package com.ranjankumarmandal.catalog_service.service;

import com.ranjankumarmandal.catalog_service.dto.event.ProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(
        topics = "product-events",
        groupId = "catalog-service-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleProductEvent(
            @Payload ProductEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {
        
        try {
            log.info("Received product event: {} from topic: {}, partition: {}, offset: {}", 
                    event, topic, partition, offset);
            
            switch (event.getEventType()) {
                case "PRODUCT_CREATED":
                    handleProductCreated(event);
                    break;
                case "PRODUCT_UPDATED":
                    handleProductUpdated(event);
                    break;
                case "PRODUCT_DELETED":
                    handleProductDeleted(event);
                    break;
                default:
                    log.warn("Unknown event type: {}", event.getEventType());
            }
            
            acknowledgment.acknowledge();
            log.info("Successfully processed and acknowledged event: {}", event);
            
        } catch (Exception e) {
            log.error("Error processing product event: {}", event, e);
            throw e;
        }
    }

    private void handleProductCreated(ProductEvent event) {
        log.info("Handling product created event for product ID: {}", event.getProductId());
    }

    private void handleProductUpdated(ProductEvent event) {
        log.info("Handling product updated event for product ID: {}", event.getProductId());
    }

    private void handleProductDeleted(ProductEvent event) {
        log.info("Handling product deleted event for product ID: {}", event.getProductId());
    }
}
