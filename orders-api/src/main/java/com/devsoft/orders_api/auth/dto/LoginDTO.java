package com.devsoft.orders_api.auth.dto;


import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
}
