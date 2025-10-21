package com.my_eshop.my_eshop.dto;

import java.util.List;

public record CreateOrderReq(
        Long userId,
        Long shippingAddressId,
        Long billingAddressId,
        List<OrderItemReq> items,
        PaymentReq payment
) {}
