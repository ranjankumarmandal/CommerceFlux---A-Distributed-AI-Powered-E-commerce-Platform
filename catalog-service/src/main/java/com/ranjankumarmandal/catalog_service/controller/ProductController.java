package com.ranjankumarmandal.catalog_service.controller;

import com.ranjankumarmandal.catalog_service.dto.request.ProductRequest;
import com.ranjankumarmandal.catalog_service.dto.response.ApiResponse;
import com.ranjankumarmandal.catalog_service.dto.response.ProductResponse;
import com.ranjankumarmandal.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ok(productService.create(request), "Product created successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.ok(productService.getById(id), "Product fetched successfully")
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAll() {
        return ResponseEntity.ok(
                ApiResponse.ok(productService.getAll(), "Products fetched successfully")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ok(productService.update(id, request), "Product updated successfully")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.ok(null, "Product deleted successfully")
        );
    }
}