package com.ranjankumarmandal.search_service.controller;

import com.ranjankumarmandal.search_service.dto.response.ApiResponse;
import com.ranjankumarmandal.search_service.dto.response.ProductSearchResponse;
import com.ranjankumarmandal.search_service.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

	private final SearchService searchService;

	@GetMapping
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<ProductSearchResponse>>> search(@RequestParam String query) {
		return ResponseEntity.ok(
				ApiResponse.ok(searchService.search(query), "Search results fetched successfully")
		);
	}

	@GetMapping("/category/{category}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<ProductSearchResponse>>> searchByCategory(@PathVariable String category) {
		return ResponseEntity.ok(
				ApiResponse.ok(searchService.searchByCategory(category), "Search results fetched successfully")
		);
	}

	@GetMapping("/brand/{brand}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<ProductSearchResponse>>> searchByBrand(@PathVariable String brand) {
		return ResponseEntity.ok(
				ApiResponse.ok(searchService.searchByBrand(brand), "Search results fetched successfully")
		);
	}

	@PostMapping("/index")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Void>> indexProduct(@RequestBody ProductSearchResponse product) {
		searchService.indexProduct(product);
		return ResponseEntity.ok(
				ApiResponse.ok(null, "Product indexed successfully")
		);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
		searchService.deleteProduct(id);
		return ResponseEntity.ok(
				ApiResponse.ok(null, "Product deleted from index successfully")
		);
	}
}
