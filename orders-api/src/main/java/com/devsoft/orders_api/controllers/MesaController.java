package com.devsoft.orders_api.controllers;

import com.devsoft.orders_api.dto.MesaDTO;
import com.devsoft.orders_api.services.MesaService;
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
public class MesaController {
    @Autowired
    private MesaService mesaService;

    @GetMapping("/mesas")
    public ResponseEntity<?> getAll(){
        List<MesaDTO> mesas = mesaService.findAll();
        return ResponseEntity.ok(mesas);
    }

}
