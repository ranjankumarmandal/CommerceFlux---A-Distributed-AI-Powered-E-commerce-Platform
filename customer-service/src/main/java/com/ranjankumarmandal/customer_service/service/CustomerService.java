package com.ranjankumarmandal.customer_service.service;

import com.ranjankumarmandal.customer_service.dto.request.CreateCustomerRequest;
import com.ranjankumarmandal.customer_service.dto.request.UpdateCustomerRequest;
import com.ranjankumarmandal.customer_service.dto.response.CustomerResponse;

import java.util.UUID;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

    CustomerResponse getById(UUID id);

    CustomerResponse getByUserId(UUID userId);

    CustomerResponse update(UUID id, UpdateCustomerRequest request);

    void delete(UUID id);
}