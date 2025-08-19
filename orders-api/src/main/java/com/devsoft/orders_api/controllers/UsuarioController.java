package com.devsoft.orders_api.controllers;

import com.devsoft.orders_api.dto.UsuarioDTO;
import com.devsoft.orders_api.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")

public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<?> getAll(){
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        return  ResponseEntity.ok(usuarios);
    }

}
