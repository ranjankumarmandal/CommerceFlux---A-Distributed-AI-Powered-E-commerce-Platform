package com.ranjankumarmandal.seller_service.repository;

import com.ranjankumarmandal.seller_service.entity.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {

    Optional<Seller> findByEmail(String email);

    Optional<Seller> findByPhone(String phone);

    Page<Seller> findByFullNameContainingIgnoreCase(String name, Pageable pageable);
}