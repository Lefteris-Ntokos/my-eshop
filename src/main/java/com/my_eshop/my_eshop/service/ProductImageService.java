package com.my_eshop.my_eshop.service;

import com.my_eshop.my_eshop.entity.Product;
import com.my_eshop.my_eshop.entity.ProductImage;
import com.my_eshop.my_eshop.repository.ProductImageRepository;
import com.my_eshop.my_eshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    private final ProductImageRepository images;
    private final ProductRepository products;
    public ProductImageService(ProductImageRepository images, ProductRepository products){ this.images=images; this.products=products; }

    public ProductImage add(Long productId, ProductImage img){
        Product p = products.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        img.setProduct(p);
        return images.save(img);
    }

    public void delete(Long imageId){ images.deleteById(imageId); }

    public List<ProductImage> listByProduct(Long productId){
        Product p = products.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return p.getImages();
    }
}

