package com.ranjankumarmandal.pricing_service.service.impl;

import com.ranjankumarmandal.pricing_service.dto.request.PriceRequest;
import com.ranjankumarmandal.pricing_service.dto.response.PriceResponse;
import com.ranjankumarmandal.pricing_service.entity.Price;
import com.ranjankumarmandal.pricing_service.enums.PricingType;
import com.ranjankumarmandal.pricing_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.pricing_service.repository.PriceRepository;
import com.ranjankumarmandal.pricing_service.service.KafkaProducerService;
import com.ranjankumarmandal.pricing_service.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

	private final PriceRepository priceRepository;
	private final KafkaProducerService kafkaProducerService;

	@Override
	@CacheEvict(value = "prices", allEntries = true)
	public PriceResponse create(PriceRequest request) {
		Price price = Price.builder()
				.productId(request.getProductId())
				.basePrice(request.getBasePrice())
				.currentPrice(request.getBasePrice())
				.discountPercentage(request.getDiscountPercentage() != null ? request.getDiscountPercentage() : BigDecimal.ZERO)
				.pricingType(PricingType.FIXED)
				.validFrom(request.getValidFrom() != null ? request.getValidFrom() : LocalDateTime.now())
				.validTo(request.getValidTo())
				.active(true)
				.currency(request.getCurrency() != null ? request.getCurrency() : "USD")
				.pricingStrategy(request.getPricingStrategy() != null ? request.getPricingStrategy() : "standard")
				.build();

		price = priceRepository.save(price);

		PriceResponse response = map(price);
		kafkaProducerService.sendPriceCreatedEvent(price.getProductId(), "Product " + price.getProductId(), response);

		return response;
	}

	@Override
	@Cacheable(value = "prices", key = "#id")
	public PriceResponse getById(Long id) {
		Price price = priceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Price not found"));
		return map(price);
	}

	@Override
	@Cacheable(value = "prices", key = "'product:' + #productId")
	public PriceResponse getByProductId(Long productId) {
		Price price = priceRepository.findByProductIdAndActiveTrue(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Price not found for product"));
		return map(price);
	}

	@Override
	@CachePut(value = "prices", key = "#id")
	public PriceResponse update(Long id, PriceRequest request) {
		Price price = priceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Price not found"));

		price.setBasePrice(request.getBasePrice());
		price.setDiscountPercentage(request.getDiscountPercentage() != null ? request.getDiscountPercentage() : BigDecimal.ZERO);
		price.setPricingStrategy(request.getPricingStrategy());
		price.setValidFrom(request.getValidFrom());
		price.setValidTo(request.getValidTo());
		price.setCurrency(request.getCurrency());
		
		recalculateCurrentPrice(price);
		price = priceRepository.save(price);

		PriceResponse response = map(price);
		kafkaProducerService.sendPriceUpdatedEvent(price.getProductId(), "Product " + price.getProductId(), response);

		return response;
	}

	@Override
	@CacheEvict(value = "prices", key = "#id")
	public void delete(Long id) {
		Price price = priceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Price not found"));
		priceRepository.delete(price);
	}

	@Override
	@Transactional
	@CachePut(value = "prices", key = "'product:' + #productId")
	public PriceResponse applyDiscount(Long productId, BigDecimal discountPercentage) {
		Price price = priceRepository.findByProductIdAndActiveTrue(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Price not found for product"));

		price.setDiscountPercentage(discountPercentage);
		recalculateCurrentPrice(price);
		price = priceRepository.save(price);

		PriceResponse response = map(price);
		kafkaProducerService.sendDiscountAppliedEvent(productId, "Product " + productId, response);

		return response;
	}

	@Override
	@CachePut(value = "prices", key = "'product:' + #productId")
	public PriceResponse calculateDynamicPrice(Long productId) {
		Price price = priceRepository.findByProductIdAndActiveTrue(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Price not found for product"));

		price.setPricingType(PricingType.DYNAMIC);
		price.setPricingStrategy("demand-based");
		
		BigDecimal dynamicAdjustment = calculateDemandBasedAdjustment();
		BigDecimal adjustedPrice = price.getBasePrice().multiply(dynamicAdjustment);
		price.setCurrentPrice(adjustedPrice.setScale(2, RoundingMode.HALF_UP));
		
		price = priceRepository.save(price);

		return map(price);
	}

	private void recalculateCurrentPrice(Price price) {
		if (price.getDiscountPercentage().compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal discountAmount = price.getBasePrice()
					.multiply(price.getDiscountPercentage())
					.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
			price.setCurrentPrice(price.getBasePrice().subtract(discountAmount));
		} else {
			price.setCurrentPrice(price.getBasePrice());
		}
	}

	private BigDecimal calculateDemandBasedAdjustment() {
		return BigDecimal.valueOf(1.0);
	}

	private PriceResponse map(Price price) {
		return PriceResponse.builder()
				.id(price.getId())
				.productId(price.getProductId())
				.basePrice(price.getBasePrice())
				.currentPrice(price.getCurrentPrice())
				.discountPercentage(price.getDiscountPercentage())
				.pricingType(price.getPricingType().name())
				.validFrom(price.getValidFrom())
				.validTo(price.getValidTo())
				.active(price.getActive())
				.currency(price.getCurrency())
				.pricingStrategy(price.getPricingStrategy())
				.build();
	}
}
