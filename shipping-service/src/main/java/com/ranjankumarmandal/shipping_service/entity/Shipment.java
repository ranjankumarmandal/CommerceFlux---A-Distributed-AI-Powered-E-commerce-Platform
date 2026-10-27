package com.ranjankumarmandal.shipping_service.entity;

import com.ranjankumarmandal.shipping_service.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shipments")
public class Shipment extends BaseEntity {

	@Column(nullable = false)
	private Long orderId;

	@Column(nullable = false, unique = true)
	private String trackingNumber;

	@Column(nullable = false)
	private Long customerId;

	@Column(nullable = false)
	private String shippingAddress;

	@Enumerated(EnumType.STRING)
	private ShippingStatus status;

	@Column
	private String carrier;

	@Column
	private LocalDateTime estimatedDeliveryDate;

	@Column
	private LocalDateTime actualDeliveryDate;
}
