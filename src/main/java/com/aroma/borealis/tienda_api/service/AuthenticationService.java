package com.aroma.borealis.tienda_api.service;

import com.aroma.borealis.tienda_api.dto.AuthResponse;
import com.aroma.borealis.tienda_api.dto.RegisterRequest;
import com.aroma.borealis.tienda_api.jwt.JwtService;
import com.aroma.borealis.tienda_api.model.Usuario;
import com.aroma.borealis.tienda_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.aroma.borealis.tienda_api.dto.LoginRequest;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterRequest request) {

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) //
                .rol(request.getRol())
                .build();

        // 2. Guardar el nuevo usuario en la base de datos
        usuarioRepository.save(usuario);

        // 3. Generar el "boleto" (token) para este nuevo usuario
        String jwtToken = jwtService.generateToken(usuario);

        // 4. Devolver el "recibo" (DTO) con el token
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(usuario);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

}