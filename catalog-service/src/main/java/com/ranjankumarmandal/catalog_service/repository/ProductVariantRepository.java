package com.ranjankumarmandal.catalog_service.repository;

import com.ranjankumarmandal.catalog_service.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
}