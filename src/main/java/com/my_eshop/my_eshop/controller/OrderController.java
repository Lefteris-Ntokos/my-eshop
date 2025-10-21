package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.dto.CreateOrderReq;
import com.my_eshop.my_eshop.entity.Order;
import com.my_eshop.my_eshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service){ this.service=service; }

    @PostMapping
    public Order create(@RequestBody CreateOrderReq req){ return service.create(req); }

    @GetMapping("/{id}")
    public ResponseEntity<Order> byId(@PathVariable Long id){
        return service.byId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-user/{userId}")
    public List<Order> byUser(@PathVariable Long userId){ return service.byUser(userId); }
}

