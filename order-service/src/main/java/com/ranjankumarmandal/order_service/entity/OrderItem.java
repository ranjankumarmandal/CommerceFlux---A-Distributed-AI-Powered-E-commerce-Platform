package com.ranjankumarmandal.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;

	@Column(nullable = false)
	private Long productId;

	@Column(nullable = false)
	private String productName;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal unitPrice;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal totalPrice;
}
