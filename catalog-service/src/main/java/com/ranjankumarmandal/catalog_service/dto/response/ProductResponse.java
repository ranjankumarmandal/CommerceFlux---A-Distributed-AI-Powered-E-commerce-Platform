package com.ranjankumarmandal.catalog_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private String status;

    private Long categoryId;

    private Long brandId;
}