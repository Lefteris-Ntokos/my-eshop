package com.my_eshop.my_eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity //του λεω οτι ειναι πινακας στη βαση
@ToString(exclude = "passwordHash")
@Table(name = "users")
public class User {

    @Id //σημαινει οτι το πεδιο id ειναι το id μας
    @GeneratedValue(strategy = GenerationType.IDENTITY) //αυτο οτι γινεται autogenerate στη βαση
    @Column(name = "users_id") // DB column
    private Long id;
    @Column(nullable = false, unique = true, length = 120)
    private String email;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    @Column(nullable = false)
    private Integer role;  // 1=USER, 2=ADMIN
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

//    @jakarta.persistence.PrePersist
//    void ensureDefaults() {
//        if (role == null) role = 1;  // default USER
//    }

    // 1->N: Users -> Addresses
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Address> addresses;

    // 1->N: Users -> Orders
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    // 1->1: Users -> Cart
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Cart cart;

    // 1->1: Users -> CustomerProfile
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private CustomerProfile customerProfile;
}
