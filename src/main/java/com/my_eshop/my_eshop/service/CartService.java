package com.my_eshop.my_eshop.service;

import com.my_eshop.my_eshop.dto.AddCartItemDto;
import com.my_eshop.my_eshop.entity.Cart;
import com.my_eshop.my_eshop.entity.CartItem;
import com.my_eshop.my_eshop.entity.Product;
import com.my_eshop.my_eshop.entity.User;
import com.my_eshop.my_eshop.repository.CartItemRepository;
import com.my_eshop.my_eshop.repository.CartRepository;
import com.my_eshop.my_eshop.repository.ProductRepository;
import com.my_eshop.my_eshop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository carts;
    private final CartItemRepository items;
    private final UserRepository users;
    private final ProductRepository products;

    public CartService(CartRepository carts, CartItemRepository items, UserRepository users, ProductRepository products){
        this.carts=carts; this.items=items; this.users=users; this.products=products;
    }

    public Cart getOrCreateCart(Long userId){
        return carts.findByUser_Id(userId).orElseGet(() -> {
            User u = users.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Cart c = new Cart(); c.setUser(u);
            return carts.save(c);
        });
    }

    @Transactional
    public Cart addItem(Long userId, AddCartItemDto dto){
        Cart c = getOrCreateCart(userId);
        // έλεγχος unique (cart_id, product_id)
        for (CartItem ci : c.getItems()){
            if (ci.getProduct().getId().equals(dto.productId())){
                ci.setQty(ci.getQty() + dto.qty());
                carts.save(c);
                return c;
            }
        }
        Product p = products.findById(dto.productId()).orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem item = new CartItem();
        item.setCart(c); item.setProduct(p); item.setQty(dto.qty());
        c.getItems().add(item);
        return carts.save(c);
    }

    @Transactional
    public Cart removeItem(Long userId, Long cartItemId){
        Cart c = getOrCreateCart(userId);
        c.getItems().removeIf(i -> i.getId().equals(cartItemId));
        return carts.save(c);
    }

    @Transactional
    public void clear(Long userId){
        Cart c = getOrCreateCart(userId);
        c.getItems().clear();
        carts.save(c);
    }
}
