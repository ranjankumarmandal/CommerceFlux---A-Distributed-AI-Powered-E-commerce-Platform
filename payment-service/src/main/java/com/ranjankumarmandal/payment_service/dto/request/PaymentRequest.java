package com.ranjankumarmandal.payment_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
	@NotNull
	private Long orderId;

	@NotNull
	private Long customerId;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;

	@NotBlank
	private String currency;

	@NotBlank
	private String paymentMethod;

	@NotBlank
	private String paymentGateway;
}
