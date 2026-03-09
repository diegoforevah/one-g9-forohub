package com.alura.foro_hub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record TopicoDto(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotBlank String curso
) {
}
