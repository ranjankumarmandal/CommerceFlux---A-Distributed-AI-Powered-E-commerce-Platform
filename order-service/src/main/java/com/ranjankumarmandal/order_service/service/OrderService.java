package com.ranjankumarmandal.order_service.service;

import com.ranjankumarmandal.order_service.dto.request.OrderRequest;
import com.ranjankumarmandal.order_service.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
	OrderResponse create(OrderRequest request);
	OrderResponse getById(Long id);
	OrderResponse getByOrderNumber(String orderNumber);
	List<OrderResponse> getByCustomerId(Long customerId);
	OrderResponse updateStatus(Long id, String status);
	void delete(Long id);
}
