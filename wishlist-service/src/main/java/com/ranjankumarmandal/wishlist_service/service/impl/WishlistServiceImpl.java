package com.ranjankumarmandal.wishlist_service.service.impl;

import com.ranjankumarmandal.wishlist_service.dto.request.WishlistItemRequest;
import com.ranjankumarmandal.wishlist_service.dto.response.WishlistItemResponse;
import com.ranjankumarmandal.wishlist_service.entity.WishlistItem;
import com.ranjankumarmandal.wishlist_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.wishlist_service.repository.WishlistRepository;
import com.ranjankumarmandal.wishlist_service.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

	private final WishlistRepository wishlistRepository;

	@Override
	@CacheEvict(value = "wishlists", key = "#request.customerId")
	public WishlistItemResponse addItem(WishlistItemRequest request) {
		if (wishlistRepository.existsByCustomerIdAndProductId(request.getCustomerId(), request.getProductId())) {
			throw new RuntimeException("Item already exists in wishlist");
		}

		WishlistItem item = WishlistItem.builder()
				.customerId(request.getCustomerId())
				.productId(request.getProductId())
				.build();

		item = wishlistRepository.save(item);
		return map(item);
	}

	@Override
	@CacheEvict(value = "wishlists", key = "#customerId")
	public void removeItem(Long id) {
		WishlistItem item = wishlistRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found"));
		wishlistRepository.delete(item);
	}

	@Override
	@Cacheable(value = "wishlists", key = "#customerId")
	public List<WishlistItemResponse> getWishlistByCustomer(Long customerId) {
		return wishlistRepository.findByCustomerId(customerId).stream()
				.map(this::map)
				.toList();
	}

	@Override
	public boolean checkItemExists(Long customerId, Long productId) {
		return wishlistRepository.existsByCustomerIdAndProductId(customerId, productId);
	}

	private WishlistItemResponse map(WishlistItem item) {
		return WishlistItemResponse.builder()
				.id(item.getId())
				.customerId(item.getCustomerId())
				.productId(item.getProductId())
				.productName("Product " + item.getProductId())
				.addedAt(item.getCreatedAt())
				.build();
	}
}
