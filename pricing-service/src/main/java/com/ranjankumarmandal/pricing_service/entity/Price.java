package com.ranjankumarmandal.pricing_service.entity;

import com.ranjankumarmandal.pricing_service.enums.PricingType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prices")
public class Price extends BaseEntity {

	@Column(nullable = false, unique = true)
	private Long productId;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal basePrice;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal currentPrice;

	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal discountPercentage;

	@Enumerated(EnumType.STRING)
	private PricingType pricingType;

	@Column
	private LocalDateTime validFrom;

	@Column
	private LocalDateTime validTo;

	@Column
	private Boolean active;

	@Column
	private String currency;

	@Column
	private String pricingStrategy;
}
