package com.my_eshop.my_eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table (name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "products_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "categories_id", nullable = false)
//    @JsonIgnore
//    private Category category;
    //

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Category category;


    @Column(name = "stock_keeping_unit", nullable = false, unique = true, length = 60)
    private String sku;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "price_cents", nullable = false)
    private Integer priceCents;

    @Column(length = 3, nullable = false)
    private String currency = "EUR";

    @Column(name = "vat_rate", nullable = false)
    private Double vatRate = 24.00;

    @Column(nullable = false)
    private Short status = 1; // 1=ACTIVE, 2=INACTIVE

    @Column(name = "stock_qty", nullable = false)
    private Integer stockQty = 0;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "products_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("products") // αγνόησε το back-reference
    private Set<Tag> tags;

}
