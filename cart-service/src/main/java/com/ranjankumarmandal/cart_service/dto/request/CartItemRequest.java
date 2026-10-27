package com.ranjankumarmandal.cart_service.dto.request;

import jakarta.validation.constraints.Min;
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
public class CartItemRequest {
	@NotNull
	private Long productId;

	@NotNull
	@Min(1)
	private Integer quantity;

	private BigDecimal price;
}
