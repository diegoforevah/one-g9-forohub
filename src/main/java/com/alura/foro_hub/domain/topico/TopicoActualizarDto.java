package com.alura.foro_hub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record TopicoActualizarDto(
        @NotNull Long id,
        String titulo,
        String mensaje
) {
}
