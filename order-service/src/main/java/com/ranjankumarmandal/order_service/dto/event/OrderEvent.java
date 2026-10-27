package com.ranjankumarmandal.order_service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
	private Long orderId;
	private String orderNumber;
	private Long customerId;
	private String eventType;
	private LocalDateTime timestamp;
	private Object payload;
}
