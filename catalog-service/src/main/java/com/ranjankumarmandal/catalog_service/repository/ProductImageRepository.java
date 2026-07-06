package com.ranjankumarmandal.catalog_service.repository;

import com.ranjankumarmandal.catalog_service.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}