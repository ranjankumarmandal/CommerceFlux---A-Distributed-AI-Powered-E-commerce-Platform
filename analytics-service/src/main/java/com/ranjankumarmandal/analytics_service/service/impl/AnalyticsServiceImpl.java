package com.ranjankumarmandal.analytics_service.service.impl;

import com.ranjankumarmandal.analytics_service.dto.response.AnalyticsResponse;
import com.ranjankumarmandal.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

	@Override
	public AnalyticsResponse getSalesAnalytics() {
		Map<String, Object> data = new HashMap<>();
		data.put("totalRevenue", 1500000.00);
		data.put("totalOrders", 5000);
		data.put("averageOrderValue", 300.00);
		data.put("conversionRate", 0.035);
		data.put("topSellingProducts", Map.of(
			"Product A", 500,
			"Product B", 450,
			"Product C", 400
		));

		return AnalyticsResponse.builder()
				.metric("sales")
				.data(data)
				.build();
	}

	@Override
	public AnalyticsResponse getCustomerAnalytics() {
		Map<String, Object> data = new HashMap<>();
		data.put("totalCustomers", 10000);
		data.put("activeCustomers", 7500);
		data.put("newCustomers", 500);
		data.put("customerRetentionRate", 0.85);
		data.put("averageCustomerLifetimeValue", 2500.00);

		return AnalyticsResponse.builder()
				.metric("customer")
				.data(data)
				.build();
	}

	@Override
	public AnalyticsResponse getProductAnalytics() {
		Map<String, Object> data = new HashMap<>();
		data.put("totalProducts", 1000);
		data.put("activeProducts", 950);
		data.put("outOfStockProducts", 50);
		data.put("averageRating", 4.2);
		data.put("topCategories", Map.of(
			"Electronics", 300,
			"Clothing", 250,
			"Home & Garden", 200
		));

		return AnalyticsResponse.builder()
				.metric("product")
				.data(data)
				.build();
	}

	@Override
	public Map<String, Object> getRealtimeMetrics() {
		Map<String, Object> metrics = new HashMap<>();
		metrics.put("activeUsers", 150);
		metrics.put("ordersPerMinute", 5);
		metrics.put("revenuePerMinute", 1500.00);
		metrics.put("serverCpuUsage", 45.5);
		metrics.put("serverMemoryUsage", 62.3);
		return metrics;
	}
}
