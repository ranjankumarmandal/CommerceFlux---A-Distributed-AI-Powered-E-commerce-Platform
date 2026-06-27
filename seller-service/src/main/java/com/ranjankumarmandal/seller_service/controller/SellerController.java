package com.ranjankumarmandal.seller_service.controller;

import com.ranjankumarmandal.seller_service.dto.request.CreateSellerRequest;
import com.ranjankumarmandal.seller_service.dto.request.UpdateSellerRequest;
import com.ranjankumarmandal.seller_service.dto.response.SellerResponse;
import com.ranjankumarmandal.seller_service.service.SellerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
@Tag(name = "Seller APIs")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<SellerResponse> create(@Valid @RequestBody CreateSellerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sellerService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(sellerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SellerResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(sellerService.getAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SellerResponse>> search(
            @RequestParam String keyword,
            Pageable pageable) {
        return ResponseEntity.ok(sellerService.search(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSellerRequest request) {
        return ResponseEntity.ok(sellerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

I am building a project for my resume targetting positions like SSE and Lead Engineer at FAANG companies with techstack - java, springboot, spring security, webflux,spring batch, postgres, radis, kafka, apache spark, python, pytorch, higging face, transformer models, tensorflow, bocker, kubernetes. Let's build step by step each servies using spring starter and other -

CommerceFlux/

identity-service/ ==> completed
customer-service/ ==> completed
seller-service/ ==> completed

catalog-service/
inventory-service/
pricing-service/

cart-service/
wishlist-service/

order-service/
payment-service/
shipping-service/

notification-service/
review-service/

search-service/
analytics-service/

api-gateway/

ai-recommendation-service/
ai-demand-forecast-service/
ai-dynamic-pricing-service/
ai-fraud-detection-service/