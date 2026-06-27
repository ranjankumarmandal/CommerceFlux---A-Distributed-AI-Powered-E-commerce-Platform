package com.ranjankumarmandal.seller_service.repository;

import com.ranjankumarmandal.seller_service.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, UUID> {
}