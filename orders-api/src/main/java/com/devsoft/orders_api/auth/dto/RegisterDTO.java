package com.devsoft.orders_api.auth.dto;

import com.devsoft.orders_api.entities.Role;
import lombok.Data;

@Data
public class RegisterDTO {
    private String nombre;
    private String username;
    private String password;
    private boolean activo;
    private Role role;
}
