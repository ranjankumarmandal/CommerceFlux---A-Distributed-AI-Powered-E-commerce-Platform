package com.ranjankumarmandal.catalog_service.dto.request;

import lombok.Data;

@Data
public class CategoryRequest {

    private String name;

    private String description;

    private Long parentCategoryId;
}