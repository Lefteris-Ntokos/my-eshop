package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.entity.Product;
import com.my_eshop.my_eshop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping("/products")
//    public String products (@RequestParam String products) {
//
//        return products;
//    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // προϊόντα με βάση το όνομα
    @GetMapping("/search")
    public List<Product> getByName(@RequestParam String name) {
        return productService.findByName(name);
    }

    @PostMapping("/category/{categoryId}")
    public Product create(@RequestBody Product product,
                          @PathVariable Long categoryId) {
        return productService.create(product, categoryId);
    }

//    @GetMapping ("/get-products")
//    public String getName (@RequestParam String getName) {
//
//        return getName;
//    }

//    @DeleteMapping("/delete-products")
//    public String deleteProducts(@RequestParam String getName) {
//
//        return getName;
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
