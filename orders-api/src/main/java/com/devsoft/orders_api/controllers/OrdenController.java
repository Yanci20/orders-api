package com.devsoft.orders_api.controllers;

import com.devsoft.orders_api.dto.CategoriaDTO;
import com.devsoft.orders_api.dto.OrdenDTO;
import com.devsoft.orders_api.entities.Orden;
import com.devsoft.orders_api.interfaces.IOrdenService;
import com.devsoft.orders_api.services.OrdenService;
import com.devsoft.orders_api.utils.EstadoOrden;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class OrdenController {
    @Autowired
    private IOrdenService ordenService;

    //endpoint para listar todas las ordenes
    @GetMapping("/ordenes")
    public ResponseEntity<?> getAll(){
        List<OrdenDTO> ordenes = ordenService.findAll();
        return ResponseEntity.ok(ordenes);
    }
    //endpoint para listar ordenes por estado
    @GetMapping("/ordenes/estado/{estado}")
    public ResponseEntity<?> getAllEstado(@PathVariable EstadoOrden estado){
        List<OrdenDTO> lista = ordenService.findByEstado(estado);
        return ResponseEntity.ok(lista);
    }
    //endpoint para obtener una orden por id
    @GetMapping("/ordenes/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            OrdenDTO dto = ordenService.findById(id);
            if(dto == null){
                response.put("message","La orden con ID".concat(id.toString()
                        .concat("no existe en la base de datos")));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //endpoin para registrar un orden
    @PostMapping("/ordenes")
    public ResponseEntity<?> register(@RequestBody OrdenDTO dto){
        Map<String, Object> response = new HashMap<>();
        try{

            OrdenDTO dtoCreado = ordenService.registerOrUpdate(dto);
            if(dtoCreado == null){
                response.put("message","No se pudo crear la orden, verifique los datos");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("message", "Orden registrada correctamente...!");
            response.put("orden", dtoCreado);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (DataAccessException e){
            response.put("message","Error al registrar la orden");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //
    @PutMapping("/ordenes/{id}")
    public ResponseEntity<?> update(@RequestBody OrdenDTO dto,
                                    @PathVariable Long id) {
        OrdenDTO ordenActual = ordenService.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (ordenActual == null) {
            response.put("message", "No se puede editar la orden con ID : "
                    .concat(id.toString().concat("No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {

            ordenActual.setClienteDTO(dto.getClienteDTO());
            ordenActual.setMesaDTO(dto.getMesaDTO());
            ordenActual.setUsuarioDTO(dto.getUsuarioDTO());
            ordenActual.setTotal(dto.getTotal());
            ordenActual.setDetalle(dto.getDetalle());
            OrdenDTO dtoUpdated = ordenService.registerOrUpdate(ordenActual);
            if (dtoUpdated == null) {
                response.put("message", "No se pudo crear la orden, verifique los datos");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("message", "Orden actualizada correctamente...!");
            response.put("orden", dtoUpdated);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar el registro, intente de nuevo");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/ordenes/cambiar-estado")
    public ResponseEntity<?> changeState(@RequestBody OrdenDTO dto){
        Map<String, Object> response = new HashMap<>();
        try{

            OrdenDTO ordenUpdated = ordenService.changeState(dto);
            if(ordenUpdated == null){
                response.put("message","Nose pudo cambiar el estado de la orden, intentelo d enuevo");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("message", "El estado de la orden cambio a: " + ordenUpdated.getEstado());
            response.put("orden", ordenUpdated);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }catch (DataAccessException e){
            response.put("message", "Error al actualizar el registro, intente de nuevo");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/ordenes/anular/{id}")
    public ResponseEntity<?> anularOrden(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        OrdenDTO ordenActual = ordenService.findById(id);

        if (ordenActual == null) {
            response.put("message", "No se puede editar la orden con ID: "
                    .concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            ordenService.anular(id);
            response.put("message", "La orden ha sido anulada");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (DataAccessException e) {
            response.put("message", "Error al anular la orden, intente de nuevo");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}