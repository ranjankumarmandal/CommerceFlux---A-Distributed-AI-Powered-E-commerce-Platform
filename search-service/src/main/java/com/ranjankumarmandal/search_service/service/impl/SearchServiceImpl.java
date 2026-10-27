package com.ranjankumarmandal.search_service.service.impl;

import com.ranjankumarmandal.search_service.document.ProductDocument;
import com.ranjankumarmandal.search_service.dto.response.ProductSearchResponse;
import com.ranjankumarmandal.search_service.repository.ProductSearchRepository;
import com.ranjankumarmandal.search_service.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

	private final ProductSearchRepository productSearchRepository;

	@Override
	public List<ProductSearchResponse> search(String query) {
		return productSearchRepository.findByNameContaining(query).stream()
				.map(this::map)
				.toList();
	}

	@Override
	public List<ProductSearchResponse> searchByCategory(String category) {
		return productSearchRepository.findByCategory(category).stream()
				.map(this::map)
				.toList();
	}

	@Override
	public List<ProductSearchResponse> searchByBrand(String brand) {
		return productSearchRepository.findByBrand(brand).stream()
				.map(this::map)
				.toList();
	}

	@Override
	public void indexProduct(ProductSearchResponse product) {
		ProductDocument document = ProductDocument.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.category(product.getCategory())
				.brand(product.getBrand())
				.price(product.getPrice())
				.status(product.getStatus())
				.categoryId(product.getCategoryId())
				.brandId(product.getBrandId())
				.build();
		productSearchRepository.save(document);
	}

	@Override
	public void deleteProduct(String id) {
		productSearchRepository.deleteById(id);
	}

	private ProductSearchResponse map(ProductDocument document) {
		return ProductSearchResponse.builder()
				.id(document.getId())
				.name(document.getName())
				.description(document.getDescription())
				.category(document.getCategory())
				.brand(document.getBrand())
				.price(document.getPrice())
				.status(document.getStatus())
				.categoryId(document.getCategoryId())
				.brandId(document.getBrandId())
				.build();
	}
}
