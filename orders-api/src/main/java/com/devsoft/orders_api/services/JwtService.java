package com.devsoft.orders_api.services;

import com.devsoft.orders_api.entities.Usuario;
import com.devsoft.orders_api.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiratio}")
    private long jwtExpiration;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuario user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado: " + username));
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getNombre())
                .build();
    }

    //
    public String generateToken(UserDetails userDetails){
        //obtenemos el usuario para agregar informacion extra al tokrn
        Usuario user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado: " +
                                userDetails.getUsername()));
        //creamos un jasmap
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("nombre", user.getNombre());
        claims.put("activo", user.isActivo());
        claims.put("role", user.getRole().getNombre());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();

    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpiated(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .build();
        return jwtParser.parseClaimsJws(token).getPayload();
    }

    public boolean isTokenExpiated(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}