package com.ranjankumarmandal.catalog_service.service.impl;

import com.ranjankumarmandal.catalog_service.dto.request.ProductRequest;
import com.ranjankumarmandal.catalog_service.dto.response.ProductResponse;
import com.ranjankumarmandal.catalog_service.entity.*;
import com.ranjankumarmandal.catalog_service.enums.ProductStatus;
import com.ranjankumarmandal.catalog_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.catalog_service.repository.*;
import com.ranjankumarmandal.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Override
    public ProductResponse create(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .brand(brand)
                .status(ProductStatus.DRAFT)
                .build();

        product = productRepository.save(product);

        return map(product);
    }

    @Override
    public ProductResponse getById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return map(product);
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());

        return map(productRepository.save(product));
    }

    @Override
    public void delete(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    private ProductResponse map(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .status(product.getStatus().name())
                .categoryId(product.getCategory().getId())
                .brandId(product.getBrand().getId())
                .build();
    }
}