package com.ranjankumarmandal.payment_service.entity;

import com.ranjankumarmandal.payment_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

	@Column(nullable = false)
	private Long orderId;

	@Column(nullable = false, unique = true)
	private String transactionId;

	@Column(nullable = false)
	private Long customerId;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	@Column
	private String currency;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@Column
	private String paymentMethod;

	@Column
	private String paymentGateway;

	@Column
	private String gatewayResponse;
}
