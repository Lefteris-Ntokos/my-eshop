package com.my_eshop.my_eshop.service;

import com.my_eshop.my_eshop.entity.Address;
import com.my_eshop.my_eshop.entity.User;
import com.my_eshop.my_eshop.repository.AddressRepository;
import com.my_eshop.my_eshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addresses;
    private final UserRepository users;
    public AddressService(AddressRepository addresses, UserRepository users){
        this.addresses=addresses; this.users=users;
    }

    public List<Address> byUser(Long userId){
        return addresses.findByUser_Id(userId);
    }

    public Address addToUser(Long userId, Address a){
        User u = users.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        a.setUser(u);
        return addresses.save(a);
    }

    public void delete(Long id){
        addresses.deleteById(id);
    }
}

