package com.tomasjuliano.foro_hub.domain.topico;

import com.tomasjuliano.foro_hub.domain.curso.DatosCurso;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotNull Long idAutor,
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull @Valid DatosCurso curso
        ) {
}
