package com.ranjankumarmandal.review_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

	@Column(nullable = false)
	private Long productId;

	@Column(nullable = false)
	private Long customerId;

	@Column(nullable = false)
	private String customerName;

	@Column(nullable = false)
	private Integer rating;

	@Column(nullable = false, length = 2000)
	private String comment;

	@Column
	private Boolean verified;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewImage> images;
}
