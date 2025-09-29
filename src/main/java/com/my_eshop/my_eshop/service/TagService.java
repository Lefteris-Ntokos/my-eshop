package com.my_eshop.my_eshop.service;

import com.my_eshop.my_eshop.entity.Product;
import com.my_eshop.my_eshop.entity.Tag;
import com.my_eshop.my_eshop.repository.ProductRepository;
import com.my_eshop.my_eshop.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tags;
    private final ProductRepository products;
    public TagService(TagRepository tags, ProductRepository products){ this.tags=tags; this.products=products; }

    public List<Tag> all(){ return tags.findAll(); }
    public Tag create(Tag t){ return tags.save(t); }
    public void delete(Long id){ tags.deleteById(id); }

    public Product attach(Long productId, String tagName){
        Product p = products.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Tag t = tags.findByName(tagName).orElseGet(() -> tags.save(new Tag(){{
            setName(tagName);
        }}));
        p.getTags().add(t);
        return products.save(p);
    }

    public Product detach(Long productId, String tagName){
        Product p = products.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        tags.findByName(tagName).ifPresent(t -> p.getTags().remove(t));
        return products.save(p);
    }
}
