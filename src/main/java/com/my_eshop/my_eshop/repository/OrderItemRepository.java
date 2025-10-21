package com.my_eshop.my_eshop.repository;

import com.my_eshop.my_eshop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

