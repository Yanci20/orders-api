package com.devsoft.orders_api.controllers;



import com.devsoft.orders_api.dto.MenuDTO;
import com.devsoft.orders_api.entities.Menu;
import com.devsoft.orders_api.interfaces.IMenuService;
import org.hibernate.sql.ast.spi.StringBuilderSqlAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @GetMapping("/menus")
    public ResponseEntity<?> getAll(){
        List<MenuDTO> menuList = menuService.findAll();
        return ResponseEntity.ok(menuList);
    }
    //metodo para obtener una menuDTO por id
    @GetMapping("/menus/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        MenuDTO menuDTO = null;
        Map<String, Object> response = new HashMap<>();
        try{
            menuDTO = menuService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(menuDTO == null){
            response.put("message", "El menu o producto con ID: "
                    .concat(id.toString().concat("no exixte en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<MenuDTO>(menuDTO, HttpStatus.OK);
    }

    //endpoint para guardar un menu
    @PostMapping("/menus")
    public ResponseEntity<?> save(@RequestPart MenuDTO dto,
                                  @RequestPart(value = "imagen", required = false)MultipartFile imageFile){
        MenuDTO menuPersisted = new MenuDTO();
        Map<String, Object> response = new HashMap<>();

        try {
            MenuDTO menuExiste = menuService.findByNombre(dto.getNombre());
            if(menuExiste != null && dto.getId() == null){
                response.put("message","Ya esxiste un menu o producto con este nombre, digite otro");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CONFLICT);
            }
            menuPersisted = menuService.save(dto, imageFile);
            response.put("message","Menu registrado correctamente...!");
            response.put("menu",menuPersisted);
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("message", "Error al insertar el registro, intente de nuevo");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //metodo para actualizar un menu
    @PutMapping("/menus/{id}")
    public ResponseEntity<?> update(@RequestPart MenuDTO dto,
                                    @RequestPart(value = "imagen", required = false) MultipartFile imageFile,
                                    @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        MenuDTO menuActual = menuService.findById(id);
        if (menuActual == null) {
            response.put("message", "No se puede editar el menú con ID: " + id + ", no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            // Validar si existe otro menú con el mismo nombre
            MenuDTO menuExistente = menuService.findByNombre(dto.getNombre());
            if (menuExistente != null && !menuExistente.getId().equals(id)) {
                response.put("message", "Ya existe un menú o producto con este nombre");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            // Forzar que el DTO tenga el ID correcto
            dto.setId(id);

            // Guardar con imagen si viene (lógica debe estar en el servicio)
            MenuDTO menuActualizado = menuService.save(dto, imageFile);

            response.put("message", "Menú actualizado correctamente...!");
            response.put("menu", menuActualizado);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar el menú, intente de nuevo");
            response.put("Error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            response.put("message", "Error al procesar la imagen");
            response.put("Error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/menus/{id}") // no usages
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        MenuDTO menuActual = menuService.findById(id);
        if (menuActual == null) {
            response.put("message", "No se puede eliminar el menú o producto con ID: "
                    .concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            menuService.delete(id);
            response.put("message", "Menú eliminado...");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            response.put("Message", "No se puede eliminar el menu" + menuActual.getNombre() +
                    ", por que esta en uso");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (DataAccessException e) {
            response.put("message", "No se puede registro de menú, ya tiene ordenes asociadas");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
