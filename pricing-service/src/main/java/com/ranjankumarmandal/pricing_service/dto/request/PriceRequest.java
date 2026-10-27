package com.ranjankumarmandal.pricing_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class PriceRequest {
	@NotNull
	private Long productId;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal basePrice;

	@DecimalMin("0")
	private BigDecimal discountPercentage;

	private String pricingStrategy;

	private LocalDateTime validFrom;

	private LocalDateTime validTo;

	private String currency;
}
