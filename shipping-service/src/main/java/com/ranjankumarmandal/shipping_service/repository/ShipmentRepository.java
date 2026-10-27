package com.ranjankumarmandal.shipping_service.repository;

import com.ranjankumarmandal.shipping_service.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
	Optional<Shipment> findByTrackingNumber(String trackingNumber);
	Optional<Shipment> findByOrderId(Long orderId);
}
