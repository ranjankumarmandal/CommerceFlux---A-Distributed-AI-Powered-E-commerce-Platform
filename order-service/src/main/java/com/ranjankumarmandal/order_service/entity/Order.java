package com.ranjankumarmandal.order_service.entity;

import com.ranjankumarmandal.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	@Column(nullable = false)
	private Long customerId;

	@Column(nullable = false, unique = true)
	private String orderNumber;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal totalAmount;

	@Column
	private String shippingAddress;

	@Column
	private String billingAddress;

	@Column
	private String paymentMethod;

	@Column
	private String paymentId;

	@Column
	private String trackingNumber;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items;
}
