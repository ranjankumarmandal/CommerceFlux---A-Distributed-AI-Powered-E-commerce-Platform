package com.ranjankumarmandal.payment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
	private Long id;
	private Long orderId;
	private String transactionId;
	private Long customerId;
	private BigDecimal amount;
	private String currency;
	private String status;
	private String paymentMethod;
	private String paymentGateway;
	private String gatewayResponse;
	private LocalDateTime createdAt;
}
