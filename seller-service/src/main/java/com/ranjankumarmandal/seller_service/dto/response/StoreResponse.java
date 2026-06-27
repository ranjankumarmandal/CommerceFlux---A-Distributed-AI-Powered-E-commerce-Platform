package com.ranjankumarmandal.seller_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreResponse {

    private String storeName;

    private String description;

    private String logoUrl;
}