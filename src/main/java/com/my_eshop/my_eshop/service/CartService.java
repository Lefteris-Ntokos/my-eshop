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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CartService {
    private final CartRepository carts;
    private final CartItemRepository items;
    private final UserRepository users;
    private final ProductRepository products;

    public CartService(CartRepository carts, CartItemRepository items, UserRepository users, ProductRepository products){
        this.carts=carts; this.items=items; this.users=users; this.products=products;
    }

//    public Cart getOrCreateCart(Long userId){
//        return carts.findByUser_Id(userId).orElseGet(() -> {
//            User u = users.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//            Cart c = new Cart(); c.setUser(u);
//            return carts.save(c);
//        });
//    }

    public Cart getOrCreateCart(Long userId) {
        User user = users.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId));
        return carts.findByUserId(userId).orElseGet(() -> {
            Cart c = new Cart();
            c.setUser(user);
            return carts.save(c);
        });
    }

//    @Transactional
//    public Cart addItem(Long userId, AddCartItemDto dto){
//        Cart c = getOrCreateCart(userId);
//        // έλεγχος unique (cart_id, product_id)
//        for (CartItem ci : c.getItems()){
//            if (ci.getProduct().getId().equals(dto.productId())){
//                ci.setQty(ci.getQty() + dto.qty());
//                carts.save(c);
//                return c;
//            }
//        }
//        Product p = products.findById(dto.productId()).orElseThrow(() -> new RuntimeException("Product not found"));
//        CartItem item = new CartItem();
//        item.setCart(c); item.setProduct(p); item.setQty(dto.qty());
//        c.getItems().add(item);
//        return carts.save(c);
//    }
//
//    @Transactional
//    public Cart removeItem(Long userId, Long cartItemId){
//        Cart c = getOrCreateCart(userId);
//        c.getItems().removeIf(i -> i.getId().equals(cartItemId));
//        return carts.save(c);
//    }
//
//    @Transactional
//    public void clear(Long userId){
//        Cart c = getOrCreateCart(userId);
//        c.getItems().clear();
//        carts.save(c);
//    }

    @Transactional
    public Cart addItem(Long userId, AddCartItemDto dto) {
        if (dto == null || dto.qty() == null || dto.qty() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "qty must be > 0");
        }

        Product product = products.findById(dto.productId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + dto.productId()));

        Cart cart = getOrCreateCart(userId);

        CartItem item = items.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElseGet(() -> {
                    CartItem ci = new CartItem();
                    ci.setCart(cart);
                    ci.setProduct(product);
                    ci.setQty(0);
                    return ci;
                });

        item.setQty(item.getQty() + dto.qty());
        items.save(item);

        return carts.findById(cart.getId()).orElse(cart);
    }

    @Transactional
    public void removeItem(Long userId, Long cartItemId) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = items.findById(cartItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CartItem not found: " + cartItemId));
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item does not belong to user's cart");
        }
        items.delete(item);
    }

    @Transactional
    public void clear(Long userId) {
        Cart cart = getOrCreateCart(userId);
        items.deleteAll(items.findByCartId(cart.getId()));
    }
}
