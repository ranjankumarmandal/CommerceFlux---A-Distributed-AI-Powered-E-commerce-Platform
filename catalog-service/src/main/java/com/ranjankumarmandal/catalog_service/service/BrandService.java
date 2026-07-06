package com.ranjankumarmandal.catalog_service.service;

import com.ranjankumarmandal.catalog_service.dto.request.BrandRequest;
import com.ranjankumarmandal.catalog_service.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {

    BrandResponse createBrand(BrandRequest request);

    BrandResponse getBrandById(Long id);

    List<BrandResponse> getAllBrands();

    BrandResponse updateBrand(Long id, BrandRequest request);

    void deleteBrand(Long id);
}