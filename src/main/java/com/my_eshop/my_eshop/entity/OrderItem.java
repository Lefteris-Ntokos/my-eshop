package com.my_eshop.my_eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_items_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Αναφορά στο προϊόν (όχι υποχρεωτικά για ιστορικότητα, αλλά υπάρχει στο schema)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "stock_keeping_unit", nullable = false, length = 60)
    private String skuSnapshot;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(name = "unit_price_cents", nullable = false)
    private Integer unitPriceCents;

    @Column(name = "vat_rate", nullable = false)
    private Double vatRate;

    @Column(nullable = false)
    private Integer qty;
}


