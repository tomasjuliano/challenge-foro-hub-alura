package com.tomasjuliano.foro_hub.domain.topico;

import com.tomasjuliano.foro_hub.domain.curso.Curso;
import jakarta.validation.Valid;

public record DatosActualizacionTopico(
        String titulo,
        String mensaje,
        @Valid Curso curso
) {
}
