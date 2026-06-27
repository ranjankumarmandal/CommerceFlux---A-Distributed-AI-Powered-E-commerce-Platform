package com.ranjankumarmandal.seller_service.service;

import com.ranjankumarmandal.seller_service.dto.request.CreateSellerRequest;
import com.ranjankumarmandal.seller_service.dto.request.UpdateSellerRequest;
import com.ranjankumarmandal.seller_service.dto.response.SellerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SellerService {

    SellerResponse create(CreateSellerRequest request);

    SellerResponse getById(UUID id);

    Page<SellerResponse> getAll(Pageable pageable);

    Page<SellerResponse> search(String keyword, Pageable pageable);

    SellerResponse update(UUID id, UpdateSellerRequest request);

    void delete(UUID id);
}