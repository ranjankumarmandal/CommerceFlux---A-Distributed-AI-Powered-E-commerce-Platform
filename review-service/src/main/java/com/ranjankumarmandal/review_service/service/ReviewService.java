package com.ranjankumarmandal.review_service.service;

import com.ranjankumarmandal.review_service.dto.request.ReviewRequest;
import com.ranjankumarmandal.review_service.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {
	ReviewResponse create(ReviewRequest request);
	ReviewResponse getById(Long id);
	List<ReviewResponse> getByProductId(Long productId);
	List<ReviewResponse> getByCustomerId(Long customerId);
	void delete(Long id);
}
