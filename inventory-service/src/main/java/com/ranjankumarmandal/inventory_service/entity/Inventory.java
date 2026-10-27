package com.ranjankumarmandal.inventory_service.entity;

import com.ranjankumarmandal.inventory_service.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventory")
public class Inventory extends BaseEntity {

	@Column(nullable = false, unique = true)
	private Long productId;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Integer reservedQuantity;

	@Column(nullable = false)
	private Integer reorderThreshold;

	@Column(nullable = false)
	private Integer reorderLevel;

	@Enumerated(EnumType.STRING)
	private InventoryStatus status;

	@Column
	private String warehouseLocation;

	@Column
	private String supplierId;
}
