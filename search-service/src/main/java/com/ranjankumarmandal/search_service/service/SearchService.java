package com.ranjankumarmandal.search_service.service;

import com.ranjankumarmandal.search_service.dto.response.ProductSearchResponse;

import java.util.List;

public interface SearchService {
	List<ProductSearchResponse> search(String query);
	List<ProductSearchResponse> searchByCategory(String category);
	List<ProductSearchResponse> searchByBrand(String brand);
	void indexProduct(ProductSearchResponse product);
	void deleteProduct(String id);
}
