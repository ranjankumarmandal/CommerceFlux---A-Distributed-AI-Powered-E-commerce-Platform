package com.ranjankumarmandal.seller_service.dto.response;

import com.ranjankumarmandal.seller_service.enums.SellerStatus;
import com.ranjankumarmandal.seller_service.enums.VerificationStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SellerResponse {

    private UUID id;

    private String fullName;

    private String email;

    private String phone;

    private String businessName;

    private String gstNumber;

    private String panNumber;

    private SellerStatus status;

    private VerificationStatus verificationStatus;
}