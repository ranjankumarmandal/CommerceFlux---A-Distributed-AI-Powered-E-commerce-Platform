package com.ranjankumarmandal.seller_service.dto.request;

import lombok.Data;

@Data
public class CreateSellerBankRequest {

    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    private String bankName;
}