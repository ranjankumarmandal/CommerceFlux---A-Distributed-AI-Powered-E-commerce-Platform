package com.ranjankumarmandal.seller_service.repository;

import com.ranjankumarmandal.seller_service.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Store, UUID> {
}