package com.ranjankumarmandal.review_service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
	@NotNull
	private Long productId;

	@NotNull
	private Long customerId;

	@NotBlank
	private String customerName;

	@NotNull
	@Min(1)
	@Max(5)
	private Integer rating;

	@NotBlank
	private String comment;
}
