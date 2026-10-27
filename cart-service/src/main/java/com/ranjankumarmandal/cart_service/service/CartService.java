package com.ranjankumarmandal.cart_service.service;

import com.ranjankumarmandal.cart_service.dto.request.CartItemRequest;
import com.ranjankumarmandal.cart_service.dto.response.CartResponse;
import reactor.core.publisher.Mono;

public interface CartService {
	Mono<CartResponse> getCart(String customerId);
	Mono<CartResponse> addItem(String customerId, CartItemRequest request);
	Mono<CartResponse> updateItem(String customerId, Long productId, Integer quantity);
	Mono<CartResponse> removeItem(String customerId, Long productId);
	Mono<CartResponse> clearCart(String customerId);
	Mono<Void> deleteCart(String customerId);
}
