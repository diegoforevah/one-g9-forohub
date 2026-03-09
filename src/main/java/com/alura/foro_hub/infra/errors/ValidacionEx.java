package com.alura.foro_hub.infra.errors;

public class ValidacionEx extends RuntimeException {
    public ValidacionEx(String mensaje) {
        super(mensaje);
    }
}
