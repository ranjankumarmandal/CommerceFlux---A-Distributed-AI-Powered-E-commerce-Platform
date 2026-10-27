package com.ranjankumarmandal.review_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
	private Long id;
	private Long productId;
	private Long customerId;
	private String customerName;
	private Integer rating;
	private String comment;
	private Boolean verified;
	private LocalDateTime createdAt;
}
