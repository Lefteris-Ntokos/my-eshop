package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.entity.Product;
import com.my_eshop.my_eshop.entity.Tag;
import com.my_eshop.my_eshop.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService service;

    public TagController(TagService service){
        this.service=service;
    }

    @GetMapping
    public List<Tag> all(){
        return service.all();
    }

    @PostMapping
    public Tag create(@RequestBody Tag t){
        return service.create(t);
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){ service.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping ("/attach")
//    public Product attach(@RequestParam Long productId, @RequestParam String tag){
//        return service.attach(productId, tag);
//    }
//
//    @PostMapping("/detach")
//    public Product detach(@RequestParam Long productId, @RequestParam String tag){
//        return service.detach(productId, tag);
//    }
}

