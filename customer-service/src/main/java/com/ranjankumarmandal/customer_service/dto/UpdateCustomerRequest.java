package com.ranjankumarmandal.customer_service.dto;

import jakarta.validation.constraints.Email;

public record UpdateCustomerRequest(

        String firstName,

        String lastName,

        @Email
        String email,

        String phone
) {}