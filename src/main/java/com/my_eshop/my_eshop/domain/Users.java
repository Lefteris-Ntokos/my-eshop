package com.my_eshop.my_eshop.domain;

import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class Users {

    private int user_id;
    private String email;
    private String username;
    private String password_hash;
    private String role;
    private DateTimeFormatter created_at;
    private DateTimeFormatter updated_at;
    private CustomerProfiles info;
}
