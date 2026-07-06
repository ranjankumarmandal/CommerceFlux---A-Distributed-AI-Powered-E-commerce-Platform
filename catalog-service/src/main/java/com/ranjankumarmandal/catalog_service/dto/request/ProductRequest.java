package com.ranjankumarmandal.catalog_service.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {

    private String name;

    private String description;

    private Long categoryId;

    private Long brandId;
}