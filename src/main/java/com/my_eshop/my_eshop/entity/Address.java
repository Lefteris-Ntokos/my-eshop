package com.my_eshop.my_eshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addresses_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false) // FK σε users.users_id
    private User user;

    @Column(nullable = false)
    private Short type; // 1=SHIPPING, 2=BILLING

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(length = 100, nullable = false)
    private String city;

    @Column(length = 100)
    private String region;

    @Column(length = 20)
    private String postal;

    @Column(length = 2, nullable = false)
    private String country = "GR";

    @Column(length = 255)
    private String notes;
}
