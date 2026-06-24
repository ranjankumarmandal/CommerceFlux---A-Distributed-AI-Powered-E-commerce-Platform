package com.ranjankumarmandal.customer_service.controller;

import com.ranjankumarmandal.customer_service.dto.request.CreateCustomerRequest;
import com.ranjankumarmandal.customer_service.dto.request.UpdateCustomerRequest;
import com.ranjankumarmandal.customer_service.dto.response.CustomerResponse;
import com.ranjankumarmandal.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public CustomerResponse create(@Valid @RequestBody CreateCustomerRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public CustomerResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping("/user/{userId}")
    public CustomerResponse getByUserId(@PathVariable UUID userId) {
        return service.getByUserId(userId);
    }

    @PutMapping("/{id}")
    public CustomerResponse update(
            @PathVariable UUID id,
            @RequestBody UpdateCustomerRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}