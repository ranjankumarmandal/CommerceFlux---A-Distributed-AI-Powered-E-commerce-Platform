package com.ranjankumarmandal.review_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "review_images")
public class ReviewImage extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Review review;

	@Column(nullable = false)
	private String imageUrl;
}
