package com.ranjankumarmandal.seller_service.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateSellerRequest {

    private String fullName;

    @Email
    private String email;

    private String phone;

    private String businessName;

    private String gstNumber;

    private String panNumber;
}