package com.ranjankumarmandal.customer_service.repository;

import com.ranjankumarmandal.customer_service.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByUserId(UUID userId);

    boolean existsByEmail(String email);

    boolean existsByUserId(UUID userId);
}