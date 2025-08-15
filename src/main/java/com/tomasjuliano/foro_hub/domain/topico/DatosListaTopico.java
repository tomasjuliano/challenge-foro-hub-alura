package com.tomasjuliano.foro_hub.domain.topico;

import com.tomasjuliano.foro_hub.domain.curso.Curso;
import com.tomasjuliano.foro_hub.domain.curso.DatosCurso;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosListaTopico (
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull LocalDateTime fechaCreacion,
        @NotNull Estado estado,
        @NotBlank String nombreAutor,
        @NotNull @Valid DatosCurso curso
) {

    public DatosListaTopico(Topico topico) {
        this(topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getAutor().getNombre(),
                new DatosCurso(topico.getCurso().getId(),
                        topico.getCurso().getNombre(),
                        topico.getCurso().getCategoria()));
    }

}
