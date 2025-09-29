package com.my_eshop.my_eshop.service;

import com.my_eshop.my_eshop.entity.Category;
import com.my_eshop.my_eshop.entity.Product;
import com.my_eshop.my_eshop.repository.CategoryRepository;
import com.my_eshop.my_eshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product create(Product p, Long categoryId){
        Category c = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        p.setCategory(c);
        return productRepository.save(p);
    }

    public Product update(Long id, Product in, Long categoryId){
        return productRepository.findById(id).map(p -> {
            if (categoryId!=null){
                Category c = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
                p.setCategory(c);
            }
            p.setSku(in.getSku());
            p.setName(in.getName());
            p.setDescription(in.getDescription());
            p.setPriceCents(in.getPriceCents());
            p.setCurrency(in.getCurrency());
            p.setVatRate(in.getVatRate());
            p.setStatus(in.getStatus());
            p.setStockQty(in.getStockQty());
            return productRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found: "+id));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
