package com.ranjankumarmandal.wishlist_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemRequest {
	@NotNull
	private Long customerId;
	
	@NotNull
	private Long productId;
}
