package com.ranjankumarmandal.seller_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerServiceImpl implements SellerService {

    private final SellerRepository repository;
    private final SellerMapper mapper;

    @Override
    public SellerResponse create(CreateSellerRequest request) {

        repository.findByEmail(request.getEmail())
                .ifPresent(s -> {
                    throw new DuplicateResourceException("Email already exists");
                });

        repository.findByPhone(request.getPhone())
                .ifPresent(s -> {
                    throw new DuplicateResourceException("Phone already exists");
                });

        Seller seller = mapper.toEntity(request);

        seller.setStatus(SellerStatus.PENDING);
        seller.setVerificationStatus(VerificationStatus.PENDING);

        return mapper.toResponse(repository.save(seller));
    }

    @Override
    @Transactional(readOnly = true)
    public SellerResponse getById(UUID id) {

        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seller not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SellerResponse> getAll(Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SellerResponse> search(String keyword, Pageable pageable) {

        return repository.findByFullNameContainingIgnoreCase(keyword, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public SellerResponse update(UUID id, UpdateSellerRequest request) {

        Seller seller = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seller not found"));

        if (request.getFullName() != null)
            seller.setFullName(request.getFullName());

        if (request.getEmail() != null)
            seller.setEmail(request.getEmail());

        if (request.getPhone() != null)
            seller.setPhone(request.getPhone());

        if (request.getBusinessName() != null)
            seller.setBusinessName(request.getBusinessName());

        if (request.getGstNumber() != null)
            seller.setGstNumber(request.getGstNumber());

        if (request.getPanNumber() != null)
            seller.setPanNumber(request.getPanNumber());

        return mapper.toResponse(repository.save(seller));
    }

    @Override
    public void delete(UUID id) {

        Seller seller = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seller not found"));

        repository.delete(seller);
    }
}