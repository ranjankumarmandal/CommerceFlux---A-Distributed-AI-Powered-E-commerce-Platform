package com.ranjankumarmandal.pricing_service.service;

import com.ranjankumarmandal.pricing_service.dto.request.PriceRequest;
import com.ranjankumarmandal.pricing_service.dto.response.PriceResponse;

import java.math.BigDecimal;

public interface PricingService {
	PriceResponse create(PriceRequest request);
	PriceResponse getById(Long id);
	PriceResponse getByProductId(Long productId);
	PriceResponse update(Long id, PriceRequest request);
	void delete(Long id);
	PriceResponse applyDiscount(Long productId, BigDecimal discountPercentage);
	PriceResponse calculateDynamicPrice(Long productId);
}
