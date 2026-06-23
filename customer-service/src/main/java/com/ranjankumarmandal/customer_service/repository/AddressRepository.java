package com.ranjankumarmandal.customer_service.repository;

import com.ranjankumarmandal.customer_service.entity.Address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findByCustomerId(UUID customerId);
}