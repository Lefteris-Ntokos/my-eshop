package com.my_eshop.my_eshop.repository;

import com.my_eshop.my_eshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
