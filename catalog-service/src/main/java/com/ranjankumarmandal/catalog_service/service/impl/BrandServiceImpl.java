package com.ranjankumarmandal.catalog_service.service.impl;

import com.ranjankumarmandal.catalog_service.dto.request.BrandRequest;
import com.ranjankumarmandal.catalog_service.dto.response.BrandResponse;
import com.ranjankumarmandal.catalog_service.entity.Brand;
import com.ranjankumarmandal.catalog_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.catalog_service.repository.BrandRepository;
import com.ranjankumarmandal.catalog_service.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public BrandResponse createBrand(BrandRequest request) {

        Brand brand = Brand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .logoUrl(request.getLogoUrl())
                .build();

        brand = brandRepository.save(brand);

        return mapToResponse(brand);
    }

    @Override
    public BrandResponse getBrandById(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        return mapToResponse(brand);
    }

    @Override
    public List<BrandResponse> getAllBrands() {

        return brandRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandRequest request) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        brand.setLogoUrl(request.getLogoUrl());

        brand = brandRepository.save(brand);

        return mapToResponse(brand);
    }

    @Override
    public void deleteBrand(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        brandRepository.delete(brand);
    }

    private BrandResponse mapToResponse(Brand brand) {

        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .logoUrl(brand.getLogoUrl())
                .build();
    }
}