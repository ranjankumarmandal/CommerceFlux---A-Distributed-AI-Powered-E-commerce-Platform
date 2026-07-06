package com.ranjankumarmandal.catalog_service.controller;

import com.ranjankumarmandal.catalog_service.dto.request.CategoryRequest;
import com.ranjankumarmandal.catalog_service.dto.response.ApiResponse;
import com.ranjankumarmandal.catalog_service.dto.response.CategoryResponse;
import com.ranjankumarmandal.catalog_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@RequestBody CategoryRequest request) {

        return ResponseEntity.ok(
                ApiResponse.ok(categoryService.createCategory(request), "Category created successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.ok(categoryService.getCategoryById(id), "Category fetched successfully")
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.ok(categoryService.getAllCategories(), "Categories fetched successfully")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Long id,
            @RequestBody CategoryRequest request) {

        return ResponseEntity.ok(
                ApiResponse.ok(categoryService.updateCategory(id, request), "Category updated successfully")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.ok(
                ApiResponse.ok(null, "Category deleted successfully")
        );
    }
}