package com.my_eshop.my_eshop.repository;

import com.my_eshop.my_eshop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
