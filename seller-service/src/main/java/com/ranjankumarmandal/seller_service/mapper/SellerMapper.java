package com.ranjankumarmandal.seller_service.mapper;

import com.ranjankumarmandal.seller_service.dto.request.CreateSellerRequest;
import com.ranjankumarmandal.seller_service.dto.response.SellerResponse;
import com.ranjankumarmandal.seller_service.entity.Seller;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    Seller toEntity(CreateSellerRequest request);

    SellerResponse toResponse(Seller seller);
}