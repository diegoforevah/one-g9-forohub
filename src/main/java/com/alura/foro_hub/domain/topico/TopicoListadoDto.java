package com.alura.foro_hub.domain.topico;

import java.time.LocalDateTime;

public record TopicoListadoDto(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String status,
        String autor
) {
    public TopicoListadoDto(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus().toString(),
                topico.getAutor()
        );
    }
}