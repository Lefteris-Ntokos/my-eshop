package com.my_eshop.my_eshop.service;

import com.my_eshop.my_eshop.dto.CreateOrderReq;
import com.my_eshop.my_eshop.dto.OrderItemReq;
import com.my_eshop.my_eshop.dto.PaymentReq;
import com.my_eshop.my_eshop.entity.*;
import com.my_eshop.my_eshop.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orders;
    private final OrderItemRepository orderItems;
    private final PaymentRepository payments;
    private final UserRepository users;
    private final AddressRepository addresses;
    private final ProductRepository products;

    public OrderService(OrderRepository orders, OrderItemRepository orderItems, PaymentRepository payments,
                        UserRepository users, AddressRepository addresses, ProductRepository products){
        this.orders=orders; this.orderItems=orderItems; this.payments=payments;
        this.users=users; this.addresses=addresses; this.products=products;
    }

//    @Transactional
//    public Order create(CreateOrderReq req){
//        User user = users.findById(req.userId()).orElseThrow(() -> new RuntimeException("User not found"));
//        Address ship = addresses.findById(req.shippingAddressId()).orElseThrow(() -> new RuntimeException("Shipping address not found"));
//        Address bill = addresses.findById(req.billingAddressId()).orElseThrow(() -> new RuntimeException("Billing address not found"));
//
//        Order o = new Order();
//        o.setUser(user);
//        o.setOrderNumber("ES-"+System.currentTimeMillis()); // απλό demo
//        o.setStatus((short)1);
//        o.setShippingAddress(ship);
//        o.setBillingAddress(bill);
//        o.setCurrency("EUR");
//        o.setItems(new ArrayList<>());
//        o.setPayments(new ArrayList<>());
//        orders.save(o); // παίρνει id
//
//        int subtotal = 0;
//        int vat = 0;
//
//        for (OrderItemReq ir : req.items()){
//            Product p = products.findById(ir.productId()).orElseThrow(() -> new RuntimeException("Product not found: "+ir.productId()));
//            OrderItem item = new OrderItem();
//            item.setOrder(o);
//            item.setProduct(p);
//            item.setSkuSnapshot(p.getSku());
//            item.setName(p.getName());
//            item.setUnitPriceCents(p.getPriceCents());
//            item.setVatRate(p.getVatRate());
//            item.setQty(ir.qty());
//            orderItems.save(item);
//
//            subtotal += p.getPriceCents() * ir.qty();
//            vat += Math.round(p.getPriceCents() * (p.getVatRate()/100.0) * ir.qty());
//        }
//
//        int shipping = 0; // demo: 0. Βάλε λογική μεταφορικών αν θέλεις.
//        int total = subtotal + vat + shipping;
//
//        o.setSubtotalCents(subtotal);
//        o.setVatCents(vat);
//        o.setShippingCents(shipping);
//        o.setTotalCents(total);
//        orders.save(o);
//
//        if (req.payment()!=null){
//            PaymentReq pr = req.payment();
//            Payment pay = new Payment();
//            pay.setOrder(o);
//            pay.setMethod(pr.method());
//            pay.setStatus((short)3); // demo: CAPTURED
//            pay.setAmountCents(pr.amountCents()!=null? pr.amountCents() : total);
//            pay.setCurrency(pr.currency()!=null? pr.currency() : "EUR");
//            payments.save(pay);
//        }
//
//        return o;
//    }

    @Transactional
    public Order create(CreateOrderReq req){
        User user = users.findById(req.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Address ship = addresses.findById(req.shippingAddressId())
                .orElseThrow(() -> new RuntimeException("Shipping address not found"));
        Address bill = addresses.findById(req.billingAddressId())
                .orElseThrow(() -> new RuntimeException("Billing address not found"));

        Order o = new Order();
        o.setUser(user);
        o.setOrderNumber("ES-" + System.currentTimeMillis());
        o.setStatus((short) 1);               // 1=PENDING
        o.setShippingAddress(ship);
        o.setBillingAddress(bill);
        o.setCurrency("EUR");
        o.setItems(new ArrayList<>());
        o.setPayments(new ArrayList<>());

        int subtotal = 0;
        int vat = 0;

        // ΦΤΙΑΧΝΟΥΜΕ ΤΑ ITEMS ΧΩΡΙΣ ΕΝΔΙΑΜΕΣΟ save
        for (OrderItemReq ir : req.items()) {
            Product p = products.findById(ir.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + ir.productId()));

            OrderItem item = new OrderItem();
            item.setOrder(o);
            item.setProduct(p);
            item.setSkuSnapshot(p.getSku());
            item.setName(p.getName());
            item.setUnitPriceCents(p.getPriceCents());
            item.setVatRate(p.getVatRate());
            item.setQty(ir.qty());

            o.getItems().add(item); // θα σωθεί με cascade από το Order

            int lineNet = p.getPriceCents() * ir.qty();
            int lineVat = (int) Math.round(lineNet * (p.getVatRate() / 100.0));
            subtotal += lineNet;
            vat += lineVat;
        }

        int shipping = 0; // demo
        int total = subtotal + vat + shipping;

        // ΤΩΡΑ ΓΕΜΙΖΟΥΜΕ ΤΑ NOT NULL ΠΕΔΙΑ
        o.setSubtotalCents(subtotal);
        o.setVatCents(vat);
        o.setShippingCents(shipping);
        o.setTotalCents(total);

        // 1ο και μοναδικό SAVE — λόγω cascade=ALL στα items θα γίνουν persist και τα OrderItem
        o = orders.save(o);

        // Payment: amount_cents ΠΑΝΤΑ = total (αν δεν στάλθηκε)
        if (req.payment() != null) {
            PaymentReq pr = req.payment();

            Payment pay = new Payment();
            pay.setOrder(o);
            pay.setMethod(pr.method());
            pay.setStatus((short) 3); // demo: CAPTURED
            pay.setAmountCents(pr.amountCents() != null ? pr.amountCents() : total);
            pay.setCurrency(pr.currency() != null ? pr.currency() : "EUR");

            payments.save(pay); // ή o.getPayments().add(pay); orders.save(o);
        }

        return o;
    }

    public Optional<Order> byId(Long id){ return orders.findById(id); }
    public List<Order> byUser(Long userId){ return orders.findAll().stream().filter(o -> o.getUser()!=null && o.getUser().getId().equals(userId)).toList(); }
}

