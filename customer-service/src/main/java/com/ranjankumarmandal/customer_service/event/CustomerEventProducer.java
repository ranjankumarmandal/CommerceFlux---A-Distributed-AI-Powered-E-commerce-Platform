package com.ranjankumarmandal.customer_service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCustomerCreated(CustomerCreatedEvent event) {

        kafkaTemplate.send(
                KafkaTopics.CUSTOMER_CREATED,
                event.customerId().toString(),
                event
        );
    }
}