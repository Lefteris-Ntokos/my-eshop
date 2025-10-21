package com.my_eshop.my_eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payments_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "provider_ref", length = 120)
    private String providerRef;

    @Column(nullable = false)
    private Short method; // 1=CARD, 2=CASH_ON_DELIVERY, 3=PAYPAL

    @Column(nullable = false)
    private Short status; // 1=PENDING, 2=AUTHORIZED, 3=CAPTURED, 4=FAILED, 5=REFUNDED

    @Column(name = "amount_cents", nullable = false)
    private Integer amountCents;

    @Column(length = 3, nullable = false)
    private String currency = "EUR";

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
