package com.rnajankumarmandal.identity_service.dto;

import java.util.UUID;

public record RegisterResponse(
        UUID userId,
        String email
) {
}