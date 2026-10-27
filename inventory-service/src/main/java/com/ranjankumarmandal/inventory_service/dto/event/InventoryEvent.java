package com.ranjankumarmandal.inventory_service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEvent {
	private Long productId;
	private String productName;
	private String eventType;
	private LocalDateTime timestamp;
	private Object payload;
}
