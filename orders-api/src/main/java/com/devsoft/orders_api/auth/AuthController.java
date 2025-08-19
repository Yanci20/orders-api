package com.devsoft.orders_api.auth;

import com.devsoft.orders_api.auth.dto.RegisterDTO;
import com.devsoft.orders_api.repository.RoleRepository;
import com.devsoft.orders_api.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthService authService;

    //enpoint para egistro de ususario
    @PostMapping("registe")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto){
        Map<String,Object> response = new HashMap<>();
        try {
            return null;
        }catch (DataAccessException e){
            response.put("message", "Error al guardar  el usuaio");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
