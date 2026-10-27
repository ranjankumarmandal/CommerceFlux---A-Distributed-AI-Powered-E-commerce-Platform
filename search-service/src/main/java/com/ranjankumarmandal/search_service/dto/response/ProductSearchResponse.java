package com.ranjankumarmandal.search_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchResponse {
	private String id;
	private String name;
	private String description;
	private String category;
	private String brand;
	private BigDecimal price;
	private String status;
	private Long categoryId;
	private Long brandId;
}
