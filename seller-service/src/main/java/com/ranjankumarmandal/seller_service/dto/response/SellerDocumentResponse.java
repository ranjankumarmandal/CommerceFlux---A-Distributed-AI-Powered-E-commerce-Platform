package com.ranjankumarmandal.seller_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellerDocumentResponse {

    private DocumentType documentType;

    private String documentNumber;

    private String documentUrl;

    private VerificationStatus verificationStatus;
}