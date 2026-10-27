package com.ranjankumarmandal.pricing_service.dto.response;

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
public class PriceResponse {
	private Long id;
	private Long productId;
	private BigDecimal basePrice;
	private BigDecimal currentPrice;
	private BigDecimal discountPercentage;
	private String pricingType;
	private LocalDateTime validFrom;
	private LocalDateTime validTo;
	private Boolean active;
	private String currency;
	private String pricingStrategy;
}
