package com.ranjankumarmandal.catalog_service.service.impl;

import com.ranjankumarmandal.catalog_service.dto.request.ProductRequest;
import com.ranjankumarmandal.catalog_service.dto.response.ProductResponse;
import com.ranjankumarmandal.catalog_service.entity.*;
import com.ranjankumarmandal.catalog_service.enums.ProductStatus;
import com.ranjankumarmandal.catalog_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.catalog_service.repository.*;
import com.ranjankumarmandal.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    @CacheEvict(value = "products", allEntries = true)
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

        ProductResponse response = map(product);
        kafkaProducerService.sendProductCreatedEvent(product.getId(), product.getName(), response);

        return response;
    }

    @Override
    @Cacheable(value = "products", key = "#id")
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
    public Page<ProductResponse> getAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::map);
    }

    @Override
    @CachePut(value = "products", key = "#id")
    public ProductResponse update(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());

        ProductResponse response = map(productRepository.save(product));
        kafkaProducerService.sendProductUpdatedEvent(product.getId(), product.getName(), response);

        return response;
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public void delete(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        String productName = product.getName();
        productRepository.delete(product);
        kafkaProducerService.sendProductDeletedEvent(id, productName);
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