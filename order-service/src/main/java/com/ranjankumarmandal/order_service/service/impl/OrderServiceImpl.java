package com.ranjankumarmandal.order_service.service.impl;

import com.ranjankumarmandal.order_service.dto.request.OrderItemRequest;
import com.ranjankumarmandal.order_service.dto.request.OrderRequest;
import com.ranjankumarmandal.order_service.dto.response.OrderItemResponse;
import com.ranjankumarmandal.order_service.dto.response.OrderResponse;
import com.ranjankumarmandal.order_service.entity.Order;
import com.ranjankumarmandal.order_service.entity.OrderItem;
import com.ranjankumarmandal.order_service.enums.OrderStatus;
import com.ranjankumarmandal.order_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.order_service.repository.OrderItemRepository;
import com.ranjankumarmandal.order_service.repository.OrderRepository;
import com.ranjankumarmandal.order_service.service.KafkaProducerService;
import com.ranjankumarmandal.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final KafkaProducerService kafkaProducerService;

	@Override
	@Transactional
	public OrderResponse create(OrderRequest request) {
		String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

		Order order = Order.builder()
				.customerId(request.getCustomerId())
				.orderNumber(orderNumber)
				.status(OrderStatus.PENDING)
				.shippingAddress(request.getShippingAddress())
				.billingAddress(request.getBillingAddress())
				.paymentMethod(request.getPaymentMethod())
				.build();

		BigDecimal totalAmount = BigDecimal.ZERO;

		for (OrderItemRequest itemRequest : request.getItems()) {
			BigDecimal itemTotal = itemRequest.getUnitPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
			totalAmount = totalAmount.add(itemTotal);

			OrderItem item = OrderItem.builder()
					.order(order)
					.productId(itemRequest.getProductId())
					.productName(itemRequest.getProductName())
					.quantity(itemRequest.getQuantity())
					.unitPrice(itemRequest.getUnitPrice())
					.totalPrice(itemTotal)
					.build();

			order.getItems().add(item);
		}

		order.setTotalAmount(totalAmount);
		order = orderRepository.save(order);

		OrderResponse response = map(order);
		kafkaProducerService.sendOrderCreatedEvent(order.getId(), order.getOrderNumber(), order.getCustomerId(), response);

		return response;
	}

	@Override
	public OrderResponse getById(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		return map(order);
	}

	@Override
	public OrderResponse getByOrderNumber(String orderNumber) {
		Order order = orderRepository.findByOrderNumber(orderNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		return map(order);
	}

	@Override
	public List<OrderResponse> getByCustomerId(Long customerId) {
		return orderRepository.findByCustomerId(customerId).stream()
				.map(this::map)
				.toList();
	}

	@Override
	@Transactional
	public OrderResponse updateStatus(Long id, String status) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
		order = orderRepository.save(order);

		OrderResponse response = map(order);
		kafkaProducerService.sendOrderStatusUpdatedEvent(order.getId(), order.getOrderNumber(), order.getCustomerId(), response);

		return response;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		orderRepository.delete(order);
	}

	private OrderResponse map(Order order) {
		List<OrderItemResponse> itemResponses = order.getItems().stream()
				.map(this::mapItem)
				.toList();

		return OrderResponse.builder()
				.id(order.getId())
				.customerId(order.getCustomerId())
				.orderNumber(order.getOrderNumber())
				.status(order.getStatus().name())
				.totalAmount(order.getTotalAmount())
				.shippingAddress(order.getShippingAddress())
				.billingAddress(order.getBillingAddress())
				.paymentMethod(order.getPaymentMethod())
				.paymentId(order.getPaymentId())
				.trackingNumber(order.getTrackingNumber())
				.createdAt(order.getCreatedAt())
				.items(itemResponses)
				.build();
	}

	private OrderItemResponse mapItem(OrderItem item) {
		return OrderItemResponse.builder()
				.id(item.getId())
				.productId(item.getProductId())
				.productName(item.getProductName())
				.quantity(item.getQuantity())
				.unitPrice(item.getUnitPrice())
				.totalPrice(item.getTotalPrice())
				.build();
	}
}
