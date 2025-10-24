package com.my_eshop.my_eshop.dto;

import java.time.LocalDateTime;

public record OrderSummaryDto(
        Long id,
        String orderNumber,
        Short status,
        Integer subtotalCents,
        Integer vatCents,
        Integer shippingCents,
        Integer totalCents,
        String currency,
        AddressDto shippingAddress,
        AddressDto billingAddress,
        LocalDateTime createdAt
) {}

