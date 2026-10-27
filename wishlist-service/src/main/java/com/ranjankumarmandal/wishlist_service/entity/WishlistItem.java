package com.ranjankumarmandal.wishlist_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wishlist_items", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"customer_id", "product_id"})
})
public class WishlistItem extends BaseEntity {

	@Column(nullable = false)
	private Long customerId;

	@Column(nullable = false)
	private Long productId;
}
