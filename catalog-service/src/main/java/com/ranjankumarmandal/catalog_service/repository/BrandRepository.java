package com.ranjankumarmandal.catalog_service.repository;

import com.ranjankumarmandal.catalog_service.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByName(String name);
}