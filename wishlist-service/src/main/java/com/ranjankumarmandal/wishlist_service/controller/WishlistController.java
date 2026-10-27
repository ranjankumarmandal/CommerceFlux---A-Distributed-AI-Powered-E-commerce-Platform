package com.ranjankumarmandal.wishlist_service.controller;

import com.ranjankumarmandal.wishlist_service.dto.request.WishlistItemRequest;
import com.ranjankumarmandal.wishlist_service.dto.response.ApiResponse;
import com.ranjankumarmandal.wishlist_service.dto.response.WishlistItemResponse;
import com.ranjankumarmandal.wishlist_service.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

	private final WishlistService wishlistService;

	@PostMapping
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<WishlistItemResponse>> addItem(@RequestBody WishlistItemRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(wishlistService.addItem(request), "Item added to wishlist successfully")
		);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Void>> removeItem(@PathVariable Long id) {
		wishlistService.removeItem(id);
		return ResponseEntity.ok(
				ApiResponse.ok(null, "Item removed from wishlist successfully")
		);
	}

	@GetMapping("/customer/{customerId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<WishlistItemResponse>>> getWishlist(@PathVariable Long customerId) {
		return ResponseEntity.ok(
				ApiResponse.ok(wishlistService.getWishlistByCustomer(customerId), "Wishlist fetched successfully")
		);
	}

	@GetMapping("/check")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Boolean>> checkItemExists(
			@RequestParam Long customerId,
			@RequestParam Long productId) {
		return ResponseEntity.ok(
				ApiResponse.ok(wishlistService.checkItemExists(customerId, productId), "Check completed successfully")
		);
	}
}
