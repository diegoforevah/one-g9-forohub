package com.alura.foro_hub.infra.errors;

public class ExValidacion extends RuntimeException {
    public ExValidacion(String mensaje) {
        super(mensaje);
    }
}
