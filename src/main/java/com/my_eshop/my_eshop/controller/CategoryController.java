package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.entity.Category;
import com.my_eshop.my_eshop.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getOne(@PathVariable Long id) {
        return categoryService.byId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @PutMapping("/{id}") public Category update(@PathVariable Long id, @RequestBody Category c){
        return categoryService.update(id,c);
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){ categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
