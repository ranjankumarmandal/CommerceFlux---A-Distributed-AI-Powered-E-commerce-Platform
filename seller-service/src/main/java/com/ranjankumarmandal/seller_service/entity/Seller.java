package com.ranjankumarmandal.seller_service.entity;

import com.ranjankumarmandal.seller_service.enums.SellerStatus;
import com.ranjankumarmandal.seller_service.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    private String businessName;

    private String gstNumber;

    private String panNumber;

    @Enumerated(EnumType.STRING)
    private SellerStatus status;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL)
    private Store store;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Warehouse> warehouses;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<SellerDocument> documents;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL)
    private SellerBank bank;
}