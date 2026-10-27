package com.ranjankumarmandal.cart_service.controller;

import com.ranjankumarmandal.cart_service.dto.request.CartItemRequest;
import com.ranjankumarmandal.cart_service.dto.response.ApiResponse;
import com.ranjankumarmandal.cart_service.dto.response.CartResponse;
import com.ranjankumarmandal.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@GetMapping("/{customerId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public Mono<ApiResponse<CartResponse>> getCart(@PathVariable String customerId) {
		return cartService.getCart(customerId)
				.map(cart -> ApiResponse.ok(cart, "Cart fetched successfully"));
	}

	@PostMapping("/{customerId}/items")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public Mono<ApiResponse<CartResponse>> addItem(
			@PathVariable String customerId,
			@RequestBody CartItemRequest request) {
		return cartService.addItem(customerId, request)
				.map(cart -> ApiResponse.ok(cart, "Item added to cart successfully"));
	}

	@PutMapping("/{customerId}/items/{productId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public Mono<ApiResponse<CartResponse>> updateItem(
			@PathVariable String customerId,
			@PathVariable Long productId,
			@RequestParam Integer quantity) {
		return cartService.updateItem(customerId, productId, quantity)
				.map(cart -> ApiResponse.ok(cart, "Item updated in cart successfully"));
	}

	@DeleteMapping("/{customerId}/items/{productId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public Mono<ApiResponse<CartResponse>> removeItem(
			@PathVariable String customerId,
			@PathVariable Long productId) {
		return cartService.removeItem(customerId, productId)
				.map(cart -> ApiResponse.ok(cart, "Item removed from cart successfully"));
	}

	@DeleteMapping("/{customerId}/clear")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public Mono<ApiResponse<CartResponse>> clearCart(@PathVariable String customerId) {
		return cartService.clearCart(customerId)
				.map(cart -> ApiResponse.ok(cart, "Cart cleared successfully"));
	}

	@DeleteMapping("/{customerId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<ApiResponse<Void>> deleteCart(@PathVariable String customerId) {
		return cartService.deleteCart(customerId)
				.then(Mono.just(ApiResponse.ok(null, "Cart deleted successfully")));
	}
}
