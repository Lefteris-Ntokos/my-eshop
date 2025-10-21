package com.my_eshop.my_eshop.service;

import com.my_eshop.my_eshop.entity.Category;
import com.my_eshop.my_eshop.entity.Product;
import com.my_eshop.my_eshop.repository.CategoryRepository;
import com.my_eshop.my_eshop.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Category c = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found: " + categoryId));

        // δένουμε την κατηγορία (αλλιώς θα σκάσει NOT NULL στο category_id)
        p.setCategory(c);

        try {
            return productRepository.save(p);
        } catch (DataIntegrityViolationException ex) {
            // π.χ. duplicate SKU, NULL σε NOT NULL στήλη, invalid FK κ.λπ.
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Product violates constraints (e.g. duplicate SKU or missing required field).",
                    ex
            );
        }
    }

    public Product update(Long id, Product in, Long categoryId){
        return productRepository.findById(id).map(p -> {
            if (categoryId != null) {
                Category c = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Category not found: " + categoryId));
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

            try {
                return productRepository.save(p);
            } catch (DataIntegrityViolationException ex) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Product violates constraints (e.g. duplicate SKU or missing required field).",
                        ex
                );
            }
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Product not found: " + id));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
