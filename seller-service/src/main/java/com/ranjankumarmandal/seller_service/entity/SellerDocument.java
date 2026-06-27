package com.ranjankumarmandal.seller_service.entity;

import com.ranjankumarmandal.seller_service.enums.DocumentType;
import com.ranjankumarmandal.seller_service.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seller_documents")
public class SellerDocument extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String documentNumber;

    private String documentUrl;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}