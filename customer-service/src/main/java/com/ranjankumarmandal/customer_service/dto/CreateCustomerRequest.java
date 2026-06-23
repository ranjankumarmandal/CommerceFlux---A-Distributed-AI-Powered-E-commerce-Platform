package com.ranjankumarmandal.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCustomerRequest(

        @NotNull
        UUID userId,

        @NotBlank
        String firstName,

        String lastName,

        @NotBlank
        @Email
        String email,

        String phone
) {}