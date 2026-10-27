package com.ranjankumarmandal.pricing_service.repository;

import com.ranjankumarmandal.pricing_service.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
	Optional<Price> findByProductIdAndActiveTrue(Long productId);
	Optional<Price> findByProductId(Long productId);
}
