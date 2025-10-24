package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.dto.ProductSummaryDto;
import com.my_eshop.my_eshop.entity.Product;
import com.my_eshop.my_eshop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products
//    @GetMapping
//    public List<Product> getAllProducts() {
//        return productService.findAll();
//    }

    @GetMapping
    public List<ProductSummaryDto> getAllProducts() {
        return productService.findAll().stream()
                .map(p -> new ProductSummaryDto(
                        p.getId(),
                        p.getSku(),
                        p.getName(),
                        p.getPriceCents(),
                        p.getCurrency(),
                        p.getVatRate(),
                        p.getStatus(),
                        p.getStockQty()
                ))
                .toList();
    }

    // GET /api/products/search?name=...
    @GetMapping("/search")
    public List<Product> getByName(@RequestParam String name) {
        return productService.findByName(name);
    }

    // POST /api/products/category/{categoryId}
    @PostMapping("/category/{categoryId}")
    public ResponseEntity<Product> create(@PathVariable Long categoryId,
                                          @RequestBody Product product) {
        Product saved = productService.create(product, categoryId);
        return ResponseEntity.status(201).body(saved);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

