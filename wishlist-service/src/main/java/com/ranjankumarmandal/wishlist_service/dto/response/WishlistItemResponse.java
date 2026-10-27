package com.ranjankumarmandal.wishlist_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemResponse {
	private Long id;
	private Long customerId;
	private Long productId;
	private String productName;
	private LocalDateTime addedAt;
}
