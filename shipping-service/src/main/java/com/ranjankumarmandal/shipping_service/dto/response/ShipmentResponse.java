package com.ranjankumarmandal.shipping_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponse {
	private Long id;
	private Long orderId;
	private String trackingNumber;
	private Long customerId;
	private String shippingAddress;
	private String status;
	private String carrier;
	private LocalDateTime estimatedDeliveryDate;
	private LocalDateTime actualDeliveryDate;
	private LocalDateTime createdAt;
}
