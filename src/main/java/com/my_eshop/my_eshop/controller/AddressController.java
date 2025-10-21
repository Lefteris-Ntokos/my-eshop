package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.entity.Address;
import com.my_eshop.my_eshop.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin (origins = "*")
@RequestMapping("/api/users/{userId}/addresses")
public class AddressController {
    private final AddressService service;
    public AddressController(AddressService service){
        this.service=service;
    }

    @GetMapping
    public List<Address> list(@PathVariable Long userId){
        return service.byUser(userId);
    }

    @PostMapping
    public Address add(@PathVariable Long userId,@RequestBody Address a){
        return service.addToUser(userId,a);
    }
    @DeleteMapping ("/{addressId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long addressId){ service.delete(addressId);
        return ResponseEntity.noContent().build();
    }
}

