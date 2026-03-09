package com.alura.foro_hub.infra.errors;

public class TokenEx extends RuntimeException {
    public TokenEx(String mensaje) {
        super(mensaje);
    }
}
