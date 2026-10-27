package com.ranjankumarmandal.review_service.service.impl;

import com.ranjankumarmandal.review_service.dto.request.ReviewRequest;
import com.ranjankumarmandal.review_service.dto.response.ReviewResponse;
import com.ranjankumarmandal.review_service.entity.Review;
import com.ranjankumarmandal.review_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.review_service.repository.ReviewRepository;
import com.ranjankumarmandal.review_service.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;

	@Override
	@Transactional
	public ReviewResponse create(ReviewRequest request) {
		Review review = Review.builder()
				.productId(request.getProductId())
				.customerId(request.getCustomerId())
				.customerName(request.getCustomerName())
				.rating(request.getRating())
				.comment(request.getComment())
				.verified(false)
				.build();

		review = reviewRepository.save(review);
		return map(review);
	}

	@Override
	public ReviewResponse getById(Long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Review not found"));
		return map(review);
	}

	@Override
	public List<ReviewResponse> getByProductId(Long productId) {
		return reviewRepository.findByProductId(productId).stream()
				.map(this::map)
				.toList();
	}

	@Override
	public List<ReviewResponse> getByCustomerId(Long customerId) {
		return reviewRepository.findByCustomerId(customerId).stream()
				.map(this::map)
				.toList();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Review not found"));
		reviewRepository.delete(review);
	}

	private ReviewResponse map(Review review) {
		return ReviewResponse.builder()
				.id(review.getId())
				.productId(review.getProductId())
				.customerId(review.getCustomerId())
				.customerName(review.getCustomerName())
				.rating(review.getRating())
				.comment(review.getComment())
				.verified(review.getVerified())
				.createdAt(review.getCreatedAt())
				.build();
	}
}
