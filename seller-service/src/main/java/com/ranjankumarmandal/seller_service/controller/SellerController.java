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