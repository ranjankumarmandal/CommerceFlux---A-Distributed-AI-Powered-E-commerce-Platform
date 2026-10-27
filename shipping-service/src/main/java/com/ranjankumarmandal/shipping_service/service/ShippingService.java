package com.ranjankumarmandal.shipping_service.service;

import com.ranjankumarmandal.shipping_service.dto.request.ShipmentRequest;
import com.ranjankumarmandal.shipping_service.dto.response.ShipmentResponse;

public interface ShippingService {
	ShipmentResponse create(ShipmentRequest request);
	ShipmentResponse getById(Long id);
	ShipmentResponse getByTrackingNumber(String trackingNumber);
	ShipmentResponse getByOrderId(Long orderId);
	ShipmentResponse updateStatus(Long id, String status);
}
