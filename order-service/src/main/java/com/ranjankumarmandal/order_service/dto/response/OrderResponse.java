package com.ranjankumarmandal.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
	private Long id;
	private Long customerId;
	private String orderNumber;
	private String status;
	private BigDecimal totalAmount;
	private String shippingAddress;
	private String billingAddress;
	private String paymentMethod;
	private String paymentId;
	private String trackingNumber;
	private LocalDateTime createdAt;
	private List<OrderItemResponse> items;
}
