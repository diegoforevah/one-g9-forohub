package com.alura.foro_hub.controller;

import com.alura.foro_hub.domain.usuario.JWTTokenDto;
import com.alura.foro_hub.domain.usuario.Usuario;
import com.alura.foro_hub.domain.usuario.UsuarioAuthDto;
import com.alura.foro_hub.service.TokenServicio;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager manager;
    private final TokenServicio tokenServicio;

    public LoginController(AuthenticationManager manager, TokenServicio tokenServicio) {
        this.manager = manager;
        this.tokenServicio = tokenServicio;
    }

    @PostMapping
    public ResponseEntity<JWTTokenDto> autenticarUsuario(@RequestBody @Valid UsuarioAuthDto datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.clave());
        var usuarioAutenticado = manager.authenticate(authToken);
        var JWTtoken = tokenServicio.generarToken((Usuario) Objects.requireNonNull(usuarioAutenticado.getPrincipal()));
        return ResponseEntity.ok(new JWTTokenDto(JWTtoken));
    }
}