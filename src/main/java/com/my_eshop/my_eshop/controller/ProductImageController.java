package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.entity.ProductImage;
import com.my_eshop.my_eshop.service.ProductImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/api/products/{productId}/images")
public class ProductImageController {
    private final ProductImageService service;
    public ProductImageController(ProductImageService service){
        this.service=service; }

    @GetMapping
    public List<ProductImage> list(@PathVariable Long productId){
        return service.listByProduct(productId);
    }

    @PostMapping
    public ProductImage add(@PathVariable Long productId, @RequestBody ProductImage img){
        return service.add(productId,img);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId, @PathVariable Long imageId){ service.delete(imageId);
        return ResponseEntity.noContent().build();
    }
}

