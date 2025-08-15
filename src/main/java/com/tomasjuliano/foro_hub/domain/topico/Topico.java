package com.tomasjuliano.foro_hub.domain.topico;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tomasjuliano.foro_hub.domain.curso.Curso;
import com.tomasjuliano.foro_hub.domain.respuesta.Respuesta;
import com.tomasjuliano.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne // Un autor puede tener muchos tópicos
    @JoinColumn(name = "autor_id")
    private Usuario autor;


    @OneToMany(mappedBy = "topico")
    @JsonManagedReference
    private List<Respuesta> respuestas;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico(DatosRegistroTopico datos, Usuario autor) {
        this.id = null;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.estado = Estado.NO_SOLUCIONADO;
        this.autor = autor;
        this.curso = new Curso(datos.curso());
        this.respuestas = null;
    }

    public void actualizarDatos(@Valid DatosActualizacionTopico datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
        if (datos.curso() != null) {
            this.curso = datos.curso();
        }
    }
}
