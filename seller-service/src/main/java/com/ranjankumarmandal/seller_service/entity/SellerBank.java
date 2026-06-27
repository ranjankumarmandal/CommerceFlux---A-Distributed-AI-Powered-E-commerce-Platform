package com.ranjankumarmandal.seller_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seller_banks")
public class SellerBank extends BaseEntity {

    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    private String bankName;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}