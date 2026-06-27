package com.ranjankumarmandal.seller_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "warehouses")
public class Warehouse extends BaseEntity {

    private String warehouseName;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}