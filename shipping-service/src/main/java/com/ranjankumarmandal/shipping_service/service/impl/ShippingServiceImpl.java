package com.ranjankumarmandal.shipping_service.service.impl;

import com.ranjankumarmandal.shipping_service.dto.request.ShipmentRequest;
import com.ranjankumarmandal.shipping_service.dto.response.ShipmentResponse;
import com.ranjankumarmandal.shipping_service.entity.Shipment;
import com.ranjankumarmandal.shipping_service.enums.ShippingStatus;
import com.ranjankumarmandal.shipping_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.shipping_service.repository.ShipmentRepository;
import com.ranjankumarmandal.shipping_service.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

	private final ShipmentRepository shipmentRepository;

	@Override
	@Transactional
	public ShipmentResponse create(ShipmentRequest request) {
		String trackingNumber = "TRK-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();

		Shipment shipment = Shipment.builder()
				.orderId(request.getOrderId())
				.trackingNumber(trackingNumber)
				.customerId(request.getCustomerId())
				.shippingAddress(request.getShippingAddress())
				.status(ShippingStatus.PENDING)
				.carrier(request.getCarrier())
				.estimatedDeliveryDate(LocalDateTime.now().plusDays(5))
				.build();

		shipment = shipmentRepository.save(shipment);
		return map(shipment);
	}

	@Override
	public ShipmentResponse getById(Long id) {
		Shipment shipment = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));
		return map(shipment);
	}

	@Override
	public ShipmentResponse getByTrackingNumber(String trackingNumber) {
		Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));
		return map(shipment);
	}

	@Override
	public ShipmentResponse getByOrderId(Long orderId) {
		Shipment shipment = shipmentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found for order"));
		return map(shipment);
	}

	@Override
	@Transactional
	public ShipmentResponse updateStatus(Long id, String status) {
		Shipment shipment = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

		shipment.setStatus(ShippingStatus.valueOf(status.toUpperCase()));
		if (shipment.getStatus() == ShippingStatus.DELIVERED) {
			shipment.setActualDeliveryDate(LocalDateTime.now());
		}
		shipment = shipmentRepository.save(shipment);

		return map(shipment);
	}

	private ShipmentResponse map(Shipment shipment) {
		return ShipmentResponse.builder()
				.id(shipment.getId())
				.orderId(shipment.getOrderId())
				.trackingNumber(shipment.getTrackingNumber())
				.customerId(shipment.getCustomerId())
				.shippingAddress(shipment.getShippingAddress())
				.status(shipment.getStatus().name())
				.carrier(shipment.getCarrier())
				.estimatedDeliveryDate(shipment.getEstimatedDeliveryDate())
				.actualDeliveryDate(shipment.getActualDeliveryDate())
				.createdAt(shipment.getCreatedAt())
				.build();
	}
}
