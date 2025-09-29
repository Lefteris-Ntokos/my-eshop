package com.my_eshop.my_eshop.repository;

import com.my_eshop.my_eshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
