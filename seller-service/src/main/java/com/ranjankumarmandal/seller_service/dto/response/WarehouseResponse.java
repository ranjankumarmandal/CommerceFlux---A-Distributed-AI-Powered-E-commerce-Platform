package com.ranjankumarmandal.seller_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseResponse {

    private String warehouseName;

    private String city;

    private String state;

    private String country;
}