package com.ranjankumarmandal.customer_service.event;

import java.util.UUID;

public record CustomerCreatedEvent(

        UUID customerId,
        UUID userId,
        String email
) {}