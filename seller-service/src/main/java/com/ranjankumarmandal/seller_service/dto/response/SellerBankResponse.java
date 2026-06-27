package com.ranjankumarmandal.seller_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellerBankResponse {

    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    private String bankName;
}