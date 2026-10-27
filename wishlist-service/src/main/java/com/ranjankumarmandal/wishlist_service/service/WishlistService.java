package com.ranjankumarmandal.wishlist_service.service;

import com.ranjankumarmandal.wishlist_service.dto.request.WishlistItemRequest;
import com.ranjankumarmandal.wishlist_service.dto.response.WishlistItemResponse;

import java.util.List;

public interface WishlistService {
	WishlistItemResponse addItem(WishlistItemRequest request);
	void removeItem(Long id);
	List<WishlistItemResponse> getWishlistByCustomer(Long customerId);
	boolean checkItemExists(Long customerId, Long productId);
}
