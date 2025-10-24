package com.my_eshop.my_eshop.dto;

public record AddressDto(
        Long id,
        Short type,
        String address,
        String city,
        String region,
        String postal,
        String country,
        String notes
) {}

