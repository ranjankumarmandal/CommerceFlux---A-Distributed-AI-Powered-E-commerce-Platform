package com.ranjankumarmandal.cart_service.service.impl;

import com.ranjankumarmandal.cart_service.dto.request.CartItemRequest;
import com.ranjankumarmandal.cart_service.dto.response.CartItemResponse;
import com.ranjankumarmandal.cart_service.dto.response.CartResponse;
import com.ranjankumarmandal.cart_service.service.CartService;
import com.ranjankumarmandal.cart_service.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

	private final ReactiveRedisTemplate<String, Object> redisTemplate;
	private final KafkaProducerService kafkaProducerService;

	private static final String CART_KEY_PREFIX = "cart:";

	@Override
	public Mono<CartResponse> getCart(String customerId) {
		String cartKey = CART_KEY_PREFIX + customerId;
		return redisTemplate.opsForHash().entries(cartKey)
				.collectMap(
					key -> (Long) key,
					value -> (Map<String, Object>) value
				)
				.map(itemsMap -> buildCartResponse(customerId, itemsMap))
				.defaultIfEmpty(CartResponse.builder()
						.customerId(customerId)
						.items(new ArrayList<>())
						.totalItems(0)
						.totalAmount(BigDecimal.ZERO)
						.build());
	}

	@Override
	public Mono<CartResponse> addItem(String customerId, CartItemRequest request) {
		String cartKey = CART_KEY_PREFIX + customerId;
		
		Map<String, Object> itemData = Map.of(
			"productId", request.getProductId(),
			"quantity", request.getQuantity(),
			"price", request.getPrice() != null ? request.getPrice() : BigDecimal.ZERO
		);

		return redisTemplate.opsForHash().put(cartKey, request.getProductId(), itemData)
				.flatMap(success -> {
					if (Boolean.TRUE.equals(success)) {
						kafkaProducerService.sendCartItemAddedEvent(customerId, itemData);
						return getCart(customerId);
					}
					return Mono.error(new RuntimeException("Failed to add item to cart"));
				});
	}

	@Override
	public Mono<CartResponse> updateItem(String customerId, Long productId, Integer quantity) {
		String cartKey = CART_KEY_PREFIX + customerId;
		
		return redisTemplate.opsForHash().get(cartKey, productId)
				.cast(Map.class)
				.switchIfEmpty(Mono.error(new RuntimeException("Item not found in cart")))
				.flatMap(itemData -> {
					itemData.put("quantity", quantity);
					return redisTemplate.opsForHash().put(cartKey, productId, itemData);
				})
				.flatMap(success -> getCart(customerId));
	}

	@Override
	public Mono<CartResponse> removeItem(String customerId, Long productId) {
		String cartKey = CART_KEY_PREFIX + customerId;
		
		return redisTemplate.opsForHash().remove(cartKey, productId)
				.flatMap(count -> {
					if (count > 0) {
						kafkaProducerService.sendCartItemRemovedEvent(customerId, productId);
						return getCart(customerId);
					}
					return Mono.error(new RuntimeException("Item not found in cart"));
				});
	}

	@Override
	public Mono<CartResponse> clearCart(String customerId) {
		String cartKey = CART_KEY_PREFIX + customerId;
		
		return redisTemplate.delete(cartKey)
				.flatMap(deleted -> {
					if (Boolean.TRUE.equals(deleted)) {
						kafkaProducerService.sendCartClearedEvent(customerId);
						return Mono.just(CartResponse.builder()
								.customerId(customerId)
								.items(new ArrayList<>())
								.totalItems(0)
								.totalAmount(BigDecimal.ZERO)
								.build());
					}
					return Mono.error(new RuntimeException("Cart not found"));
				});
	}

	@Override
	public Mono<Void> deleteCart(String customerId) {
		String cartKey = CART_KEY_PREFIX + customerId;
		return redisTemplate.delete(cartKey).then();
	}

	private CartResponse buildCartResponse(String customerId, Map<Long, Map<String, Object>> itemsMap) {
		List<CartItemResponse> items = new ArrayList<>();
		int totalItems = 0;
		BigDecimal totalAmount = BigDecimal.ZERO;

		for (Map<String, Object> itemData : itemsMap.values()) {
			Long productId = ((Number) itemData.get("productId")).longValue();
			Integer quantity = ((Number) itemData.get("quantity")).intValue();
			BigDecimal price = itemData.get("price") != null ? new BigDecimal(itemData.get("price").toString()) : BigDecimal.ZERO;
			BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

			CartItemResponse item = CartItemResponse.builder()
					.productId(productId)
					.productName("Product " + productId)
					.quantity(quantity)
					.price(price)
					.totalPrice(totalPrice)
					.build();

			items.add(item);
			totalItems += quantity;
			totalAmount = totalAmount.add(totalPrice);
		}

		return CartResponse.builder()
				.customerId(customerId)
				.items(items)
				.totalItems(totalItems)
				.totalAmount(totalAmount)
				.build();
	}
}
