package com.devsoft.orders_api.controllers;

import com.devsoft.orders_api.dto.CategoriaDTO;
import com.devsoft.orders_api.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;

//metodo para obtener todas las categorias
    @GetMapping("/categorias")
    public ResponseEntity<?> getAll(){
        List<CategoriaDTO> categoriaDTOList = categoriaService.findAll();
        return ResponseEntity.ok(categoriaDTOList);
    }
    //metodo para obtener una categoriaDTO por id
    @GetMapping("/categorias/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        CategoriaDTO categoriaDTO = null;
        Map<String, Object> response = new HashMap<>();
        try{
            categoriaDTO = categoriaService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(categoriaDTO == null){
            response.put("message", "La categoria con ID: "
                    .concat(id.toString().concat("no exixte en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CategoriaDTO>(categoriaDTO, HttpStatus.OK);
    }

    @PostMapping("/categorias")
    public ResponseEntity<?> save(@RequestBody CategoriaDTO dto) {
        CategoriaDTO catPersisted = new CategoriaDTO();
        Map<String, Object> response = new HashMap<>();
        try {
            /*
            CategoriaDTO catExiste = categoriaService.findByNombre(dto.getNombre());
            if (catExiste != null && dto.getId() == null) {
                response.put("message", "Ya existe una categoria con este nombre, digite otro");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }*/
            catPersisted = categoriaService.save(dto);
            response.put("message", "Categoria registrada corectamente");
            response.put("categoria", catPersisted);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("message", "Error al insertar el registro, intente de nuevo");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //empoit para actualizar una categoria
    @PutMapping("/categorias/{id}")
    public ResponseEntity<?> update(@RequestBody CategoriaDTO dto,
                                    @PathVariable Long id){
        CategoriaDTO catActual = categoriaService.findById(id);

        CategoriaDTO catUpdate = null;
        Map<String, Object> response = new HashMap<>();
        if(catActual == null){
            response.put("message", "No se pude editar la categoria con ID: "
                    .concat(id.toString().concat("no exixte en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            CategoriaDTO catExiste = categoriaService.findByNombre(dto.getNombre());
            if(catExiste != null && !Objects.requireNonNull(catExiste).getId().equals(id)){
                response.put("message", "Ya existe una categoria con este nomre en la base de datos");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CONFLICT);
            }
            catActual.setNombre(dto.getNombre());
            catUpdate = categoriaService.save(catActual);

        }catch (DataAccessException e){
            response.put("message", "Error al actualizar el registro, intente de nuevo");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Categoria actualizada correctamente...!");
        response.put("categoria", catUpdate);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    //endpoint para eliminar una categoria
    @DeleteMapping("/categorias{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object>  response = new HashMap<>();
        CategoriaDTO catActual = categoriaService.findById(id);
        if(catActual == null) {
            response.put("message", "No se pude editar la categoria con ID: "
                    .concat(id.toString().concat("no exixte en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            categoriaService.delete(id);
        }catch (DataAccessException e){
            response.put("message", "No se puede eliminar la categoria, ya tiene menus asociados");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Categoria eliminada...!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
