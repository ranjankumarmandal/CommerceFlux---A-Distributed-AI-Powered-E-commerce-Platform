package com.ranjankumarmandal.seller_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateSellerRequest {

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String phone;

    @NotBlank
    private String businessName;

    private String gstNumber;

    private String panNumber;
}