package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.entity.Product;
import com.my_eshop.my_eshop.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/products")
public class ProductTagController {

    private final TagService service;

    public ProductTagController(TagService service) {
        this.service = service;
    }

    // POST /api/products/{productId}/tags/{tag}/attach
    @PostMapping("/{productId}/tags/{tag}/attach")
    public ResponseEntity<Product> attach(@PathVariable Long productId, @PathVariable String tag) {
        return ResponseEntity.ok(service.attach(productId, tag));
    }

    // POST /api/products/{productId}/tags/{tag}/detach
    @PostMapping("/{productId}/tags/{tag}/detach")
    public ResponseEntity<Product> detach(@PathVariable Long productId, @PathVariable String tag) {
        return ResponseEntity.ok(service.detach(productId, tag));
    }
}
