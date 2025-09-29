package com.my_eshop.my_eshop.repository;

import com.my_eshop.my_eshop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
