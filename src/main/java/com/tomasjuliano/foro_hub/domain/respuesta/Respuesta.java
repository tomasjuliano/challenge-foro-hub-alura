package com.tomasjuliano.foro_hub.domain.respuesta;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tomasjuliano.foro_hub.domain.topico.Topico;
import com.tomasjuliano.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String mensaje;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    @JsonBackReference
    Topico topico; // El tópico al que pertenece la respuesta

    LocalDateTime fechaCreacion; // Fecha y hora en que se creó la respuesta

    @ManyToOne
    @JoinColumn(name = "autor_id")
    Usuario autor; // Es quien responde al tópico

    Boolean solucion; // Indica si la respuesta es la solución al tópico
}
