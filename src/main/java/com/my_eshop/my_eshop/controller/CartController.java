package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.dto.AddCartItemDto;
import com.my_eshop.my_eshop.entity.Cart;
import com.my_eshop.my_eshop.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/api/users/{userId}/cart")
public class CartController {
    private final CartService service;
    public CartController(CartService service){ this.service=service; }

    @GetMapping
    public Cart get(@PathVariable Long userId){ return service.getOrCreateCart(userId); }

    @PostMapping("/items")
    public Cart addItem(@PathVariable Long userId, @RequestBody AddCartItemDto dto){
        return service.addItem(userId, dto);
    }

    @DeleteMapping("/items/{cartItemId}")
    public Cart removeItem(@PathVariable Long userId, @PathVariable Long cartItemId){
        return service.removeItem(userId, cartItemId);
    }

    @DeleteMapping public ResponseEntity<Void> clear(@PathVariable Long userId){ service.clear(userId); return ResponseEntity.noContent().build(); }
}
