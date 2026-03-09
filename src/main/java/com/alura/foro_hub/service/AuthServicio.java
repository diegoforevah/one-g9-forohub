package com.alura.foro_hub.service;

import com.alura.foro_hub.domain.usuario.UsuarioRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServicio implements UserDetailsService {
    private final UsuarioRepo repository;
    public AuthServicio(UsuarioRepo repository) {
        this.repository = repository;
    }

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }
}