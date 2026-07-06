package com.ranjankumarmandal.catalog_service.service;

import com.ranjankumarmandal.catalog_service.dto.request.ProductRequest;
import com.ranjankumarmandal.catalog_service.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse getById(Long id);

    List<ProductResponse> getAll();

    Page<ProductResponse> getAll(Pageable pageable);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
}