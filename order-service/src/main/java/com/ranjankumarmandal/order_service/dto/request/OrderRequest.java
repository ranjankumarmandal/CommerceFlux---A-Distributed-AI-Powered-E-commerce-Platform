package com.ranjankumarmandal.order_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
	@NotNull
	private Long customerId;

	@NotBlank
	private String shippingAddress;

	@NotBlank
	private String billingAddress;

	@NotBlank
	private String paymentMethod;

	@NotNull
	private List<OrderItemRequest> items;
}
