package com.my_eshop.my_eshop.service;

import com.my_eshop.my_eshop.entity.Category;
import com.my_eshop.my_eshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category create(Category c){ return categoryRepository.save(c); }
    public Category update(Long id, Category in){
        return categoryRepository.findById(id).map(c -> {
            c.setUrlFriendly(in.getUrlFriendly());
            c.setCategoryName(in.getCategoryName());
            c.setDescription(in.getDescription());
            return categoryRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Category not found: "+id));
    }
    public void delete(Long id){ categoryRepository.deleteById(id); }

}
