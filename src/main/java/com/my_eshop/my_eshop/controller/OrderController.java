package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.dto.AddressDto;
import com.my_eshop.my_eshop.dto.CreateOrderReq;
import com.my_eshop.my_eshop.dto.OrderSummaryDto;
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

    // helper mapper
    private AddressDto toAddressDto(com.my_eshop.my_eshop.entity.Address a) {
        if (a == null) return null;
        return new AddressDto(
                a.getId(),
                a.getType(),
                a.getAddress(),
                a.getCity(),
                a.getRegion(),
                a.getPostal(),
                a.getCountry(),
                a.getNotes()
        );
    }

    private OrderSummaryDto toDto(com.my_eshop.my_eshop.entity.Order o){
        return new OrderSummaryDto(
                o.getId(),
                o.getOrderNumber(),
                o.getStatus(),
                o.getSubtotalCents(),
                o.getVatCents(),
                o.getShippingCents(),
                o.getTotalCents(),
                o.getCurrency(),
                toAddressDto(o.getShippingAddress()),
                toAddressDto(o.getBillingAddress()),
                o.getCreatedAt()
        );
    }


    @PostMapping
    public Order create(@RequestBody CreateOrderReq req){ return service.create(req); }

//    @GetMapping("/{id}")
//    public ResponseEntity<Order> byId(@PathVariable Long id){
//        return service.byId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/by-user/{userId}")
//    public List<Order> byUser(@PathVariable Long userId){ return service.byUser(userId); }

    @GetMapping("/{id}")
    public ResponseEntity<OrderSummaryDto> byId(@PathVariable Long id){
        return service.byId(id)
                .map(o -> ResponseEntity.ok(toDto(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-user/{userId}")
    public List<OrderSummaryDto> byUser(@PathVariable Long userId){
        return service.byUser(userId).stream()
                .map(this::toDto)
                .toList();
    }
}

