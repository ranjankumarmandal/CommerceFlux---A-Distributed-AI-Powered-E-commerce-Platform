package com.ranjankumarmandal.catalog_service.controller;

import com.ranjankumarmandal.catalog_service.dto.request.BrandRequest;
import com.ranjankumarmandal.catalog_service.dto.response.ApiResponse;
import com.ranjankumarmandal.catalog_service.dto.response.BrandResponse;
import com.ranjankumarmandal.catalog_service.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> create(@RequestBody BrandRequest request) {

        return ResponseEntity.ok(
                ApiResponse.ok(brandService.createBrand(request), "Brand created successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.ok(brandService.getBrandById(id), "Brand fetched successfully")
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.ok(brandService.getAllBrands(), "Brands fetched successfully")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> update(
            @PathVariable Long id,
            @RequestBody BrandRequest request) {

        return ResponseEntity.ok(
                ApiResponse.ok(brandService.updateBrand(id, request), "Brand updated successfully")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        brandService.deleteBrand(id);

        return ResponseEntity.ok(
                ApiResponse.ok(null, "Brand deleted successfully")
        );
    }
}