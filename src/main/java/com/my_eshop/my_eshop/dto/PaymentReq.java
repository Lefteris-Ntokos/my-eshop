package com.my_eshop.my_eshop.dto;

public record PaymentReq(Short method, Integer amountCents, String currency) {}
