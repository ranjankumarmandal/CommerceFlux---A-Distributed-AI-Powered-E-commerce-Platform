package com.ranjankumarmandal.inventory_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
	private Long id;
	private Long productId;
	private Integer quantity;
	private Integer reservedQuantity;
	private Integer availableQuantity;
	private Integer reorderThreshold;
	private Integer reorderLevel;
	private String status;
	private String warehouseLocation;
	private String supplierId;
}
