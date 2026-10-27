package com.ranjankumarmandal.cart_service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartEvent {
	private String customerId;
	private String eventType;
	private LocalDateTime timestamp;
	private Object payload;
}
