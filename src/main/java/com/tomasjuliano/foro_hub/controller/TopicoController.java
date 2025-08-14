package com.tomasjuliano.foro_hub.controller;

import com.tomasjuliano.foro_hub.domain.ValidacionException;
import com.tomasjuliano.foro_hub.domain.curso.CursoRepository;
import com.tomasjuliano.foro_hub.domain.topico.DatosDetalleTopico;
import com.tomasjuliano.foro_hub.domain.topico.DatosRegistroTopico;
import com.tomasjuliano.foro_hub.domain.topico.Topico;
import com.tomasjuliano.foro_hub.domain.topico.TopicoRepository;
import com.tomasjuliano.foro_hub.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder) {
        // Verifico si el autor existe
        var autor = usuarioRepository.findById(datos.idAutor())
                .orElseThrow(() -> new ValidacionException("Autor inexistente."));

        // Verifico si existe el curso asociado
        if (!cursoRepository.existsById(datos.curso().getId())) {
            throw new ValidacionException("Curso inexistente.");
        }

        // Verifico si ya existe un tópico con ese título y mensaje
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Ya existe un tópico con ese título y mensaje.");
        }

        // Creo un nuevo tópico con los datos proporcionados y el autor encontrado
        Topico topico = new Topico(datos, autor);
        // Guardo el tópico en la base de datos
        topicoRepository.save(topico);

        // Construyo la URI del nuevo tópico creado
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        // Retorno una respuesta con el estado 201 (CREATED) y la ubicación del nuevo recurso
        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

}
