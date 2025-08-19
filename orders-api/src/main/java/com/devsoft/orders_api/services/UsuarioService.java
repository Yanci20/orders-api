package com.devsoft.orders_api.services;

import com.devsoft.orders_api.dto.RoleDTO;
import com.devsoft.orders_api.dto.UsuarioDTO;
import com.devsoft.orders_api.entities.Usuario;
import com.devsoft.orders_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    @Autowired
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream().map(this::toDTO).toList();
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setUsername(usuario.getUsername());
        dto.setActivo(usuario.isActivo());
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(usuario.getRole().getId());
        roleDTO.setNombre(usuario.getRole().getNombre());
        dto.setRoleDTO(roleDTO);

        return dto;
    }

}
