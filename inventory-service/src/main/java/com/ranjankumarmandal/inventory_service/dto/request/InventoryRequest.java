package com.ranjankumarmandal.inventory_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest {
	@NotNull
	private Long productId;

	@NotNull
	@Min(0)
	private Integer quantity;

	@NotNull
	@Min(0)
	private Integer reorderThreshold;

	@NotNull
	@Min(0)
	private Integer reorderLevel;

	private String warehouseLocation;

	private String supplierId;
}
