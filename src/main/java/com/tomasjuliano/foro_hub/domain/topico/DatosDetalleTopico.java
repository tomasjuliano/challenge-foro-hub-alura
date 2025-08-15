package com.tomasjuliano.foro_hub.domain.topico;

import com.tomasjuliano.foro_hub.domain.curso.DatosCurso;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Estado estado,
        String nombreAutor,
        DatosCurso curso
) {
    public DatosDetalleTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getAutor().getNombre(),
                new DatosCurso(topico.getCurso().getId(),
                        topico.getCurso().getNombre(),
                        topico.getCurso().getCategoria())
        );
    }
}
