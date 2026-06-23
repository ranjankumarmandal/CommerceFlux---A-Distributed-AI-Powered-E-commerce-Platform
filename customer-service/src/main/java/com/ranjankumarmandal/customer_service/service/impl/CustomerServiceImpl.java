package com.ranjankumarmandal.customer_service.service.impl;

import com.ranjankumarmandal.customer_service.dto.CreateCustomerRequest;
import com.ranjankumarmandal.customer_service.dto.UpdateCustomerRequest;
import com.ranjankumarmandal.customer_service.dto.CustomerResponse;
import com.ranjankumarmandal.customer_service.entity.Customer;
import com.ranjankumarmandal.customer_service.entity.CustomerStatus;
import com.ranjankumarmandal.customer_service.exception.DuplicateResourceException;
import com.ranjankumarmandal.customer_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.customer_service.mapper.CustomerMapper;
import com.ranjankumarmandal.customer_service.repository.CustomerRepository;
import com.ranjankumarmandal.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerResponse create(CreateCustomerRequest request) {

        if (repository.existsByUserId(request.userId())) {
            throw new DuplicateResourceException("Customer already exists for userId");
        }

        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .userId(request.userId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .loyaltyPoints(0)
                .status(CustomerStatus.ACTIVE)
                .build();

        return mapper.toResponse(repository.save(customer));
    }

    @Override
    public CustomerResponse getById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public CustomerResponse getByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .map(mapper::toResponse)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public CustomerResponse update(UUID id, UpdateCustomerRequest request) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        if (request.firstName() != null)
            customer.setFirstName(request.firstName());

        if (request.lastName() != null)
            customer.setLastName(request.lastName());

        if (request.email() != null)
            customer.setEmail(request.email());

        if (request.phone() != null)
            customer.setPhone(request.phone());

        return mapper.toResponse(repository.save(customer));
    }

    @Override
    public void delete(UUID id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        customer.setStatus(CustomerStatus.DELETED);
        repository.save(customer);
    }
}