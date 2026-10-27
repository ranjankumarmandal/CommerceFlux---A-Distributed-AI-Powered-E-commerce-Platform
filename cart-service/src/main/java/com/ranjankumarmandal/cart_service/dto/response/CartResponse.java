package com.ranjankumarmandal.cart_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
	private String customerId;
	private List<CartItemResponse> items;
	private Integer totalItems;
	private BigDecimal totalAmount;
}
