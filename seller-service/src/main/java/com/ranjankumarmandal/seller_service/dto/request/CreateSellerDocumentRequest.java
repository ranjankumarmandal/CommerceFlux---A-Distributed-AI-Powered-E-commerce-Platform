package com.ranjankumarmandal.seller_service.dto.request;

import lombok.Data;

@Data
public class CreateSellerDocumentRequest {

    private DocumentType documentType;

    private String documentNumber;

    private String documentUrl;
}