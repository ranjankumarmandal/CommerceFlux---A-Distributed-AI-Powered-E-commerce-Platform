package com.ranjankumarmandal.seller_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stores")
public class Store extends BaseEntity {

    private String storeName;

    private String description;

    private String logoUrl;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}