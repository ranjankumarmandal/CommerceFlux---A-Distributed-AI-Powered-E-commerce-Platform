package com.ranjankumarmandal.customer_service.dto.response;

import com.ranjankumarmandal.customer_service.entity.CustomerStatus;

import java.time.Instant;
import java.util.UUID;

public record CustomerResponse(

        UUID id,
        UUID userId,
        String firstName,
        String lastName,
        String email,
        String phone,
        Integer loyaltyPoints,
        CustomerStatus status,
        Instant createdAt,
        Instant updatedAt
) {}