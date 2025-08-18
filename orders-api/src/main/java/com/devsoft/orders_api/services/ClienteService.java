package com.devsoft.orders_api.services;

import com.devsoft.orders_api.dto.ClienteDTO;
import com.devsoft.orders_api.entities.Cliente;
import com.devsoft.orders_api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> findAll(){
        return clienteRepository.findAll().stream().map(this::toDTO).toList();
    }

    private ClienteDTO toDTO(Cliente cliente){
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        dto.setTipoCliente(cliente.getTipoCliente());
        return dto;
    }
}
