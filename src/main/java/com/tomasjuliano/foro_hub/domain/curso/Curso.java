package com.tomasjuliano.foro_hub.domain.curso;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    Long id;

    @NotBlank
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Curso(DatosCurso datos) {
        this.id = datos.id();
        this.nombre = datos.nombre();
        this.categoria = datos.categoria();
    }
}
