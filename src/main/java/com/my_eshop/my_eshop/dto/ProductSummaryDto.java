package com.my_eshop.my_eshop.dto;

public record ProductSummaryDto(
        Long id,
        String sku,
        String name,
        Integer priceCents,
        String currency,
        Double vatRate,
        Short status,
        Integer stockQty
) {}

