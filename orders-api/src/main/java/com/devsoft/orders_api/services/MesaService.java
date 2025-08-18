package com.devsoft.orders_api.services;

import com.devsoft.orders_api.dto.MesaDTO;
import com.devsoft.orders_api.entities.Mesa;
import com.devsoft.orders_api.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    public List<MesaDTO> findAll(){
        return mesaRepository.findAll().stream().map(this::toDTO).toList();
    }

    private MesaDTO toDTO(Mesa mesa){
        MesaDTO dto = new MesaDTO();
        dto.setId(mesa.getId());
        dto.setNumero(mesa.getNumero());
        dto.setUbicacion(mesa.getUbicacion());
        return dto;
    }

}
