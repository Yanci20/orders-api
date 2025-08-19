package com.devsoft.orders_api.dto;

import com.devsoft.orders_api.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String username;
    private boolean activo;
    private RoleDTO roleDTO;
}
