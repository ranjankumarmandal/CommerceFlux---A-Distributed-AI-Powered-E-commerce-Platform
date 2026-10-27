package com.ranjankumarmandal.analytics_service.service;

import com.ranjankumarmandal.analytics_service.dto.response.AnalyticsResponse;

import java.util.Map;

public interface AnalyticsService {
	AnalyticsResponse getSalesAnalytics();
	AnalyticsResponse getCustomerAnalytics();
	AnalyticsResponse getProductAnalytics();
	Map<String, Object> getRealtimeMetrics();
}
