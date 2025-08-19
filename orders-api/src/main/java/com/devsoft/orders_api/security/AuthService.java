package com.devsoft.orders_api.security;

import com.devsoft.orders_api.auth.dto.JwtResponse;
import com.devsoft.orders_api.auth.dto.LoginDTO;
import com.devsoft.orders_api.auth.dto.RegisterDTO;
import com.devsoft.orders_api.entities.Role;
import com.devsoft.orders_api.entities.Usuario;
import com.devsoft.orders_api.repository.RoleRepository;
import com.devsoft.orders_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private  final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;

    //metodo para registro de usuario
    public JwtResponse register(RegisterDTO registerDTO){
        Role role = roleRepository.findByNombre(registerDTO.getRole().getNombre())
                .orElseThrow(() -> new RuntimeException("Role no encontrado"));
        //
        Usuario user = new Usuario();
        user.setNombre(registerDTO.getNombre());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setActivo(registerDTO.isActivo());
        user.setRole(registerDTO.getRole());
        //guardamos el usuario
        String token = jwtService.generateToken(
                User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(role.getNombre())
                        .build()
        );
        return new JwtResponse(token);
    }

    //metodo para autenticacion de usuario
    public JwtResponse authenticate(LoginDTO dto){
        Usuario user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Credenciales invalidas"));
        if (passwordEncoder.matches(dto.getPassword(), user.getUsername())){
            throw  new RuntimeException("Credenciales invalidas");
        }
        String token = jwtService.generateToken(
                User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().getNombre())
                        .build()
        );
        return  new JwtResponse(token);
    }

    //
}
