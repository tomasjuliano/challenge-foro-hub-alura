package com.tomasjuliano.foro_hub.controller;

import com.tomasjuliano.foro_hub.domain.ValidacionException;
import com.tomasjuliano.foro_hub.domain.curso.CursoRepository;
import com.tomasjuliano.foro_hub.domain.topico.*;
import com.tomasjuliano.foro_hub.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
        // Busco el curso por id
        var curso = cursoRepository.findById(datos.curso().id())
                .orElseThrow(() -> new ValidacionException("Curso inexistente."));

        // Comparo nombre y categoría
        if (!curso.getNombre().equals(datos.curso().nombre()) ||
                !curso.getCategoria().equals(datos.curso().categoria())) {
            throw new ValidacionException("La ID del curso no coincide con los datos registrados.");
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

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listar(@PageableDefault(size=10, sort={"fechaCreacion"}) Pageable paginacion){
        var page = topicoRepository.findAll(paginacion).map(DatosListaTopico::new);

        if (page.getTotalElements() == 0) {
            throw new ValidacionException("No hay tópicos registrados.");
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var topico = topicoRepository.findById(id);
        if (topico.isEmpty()) {
            throw new ValidacionException("Tópico inexistente.");
        }

        return ResponseEntity.ok(new DatosDetalleTopico(topico.get()));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Tópico inexistente."));

        // Verifico si el título y mensaje son iguales
        if (topico.getTitulo().equals(datos.titulo()) && topico.getMensaje().equals(datos.mensaje())) {
            throw new ValidacionException("No se han realizado cambios en el tópico.");
        }

        if (topico.getCurso() != datos.curso()){
            // Verifico si existe el curso asociado
            var curso = cursoRepository.findById(topico.getCurso().getId())
                    .orElseThrow(() -> new ValidacionException("Curso inexistente."));
        } else{
            throw new ValidacionException("No se han realizado cambios en el curso del tópico. Excluir de la petición.");
        }

        // Actualizo los datos del tópico
        topico.actualizarDatos(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Tópico inexistente."));

        // Elimino el tópico de la base de datos
        topicoRepository.delete(topico);

        // Retorno una respuesta vacía con el estado 204 (NO CONTENT)
        return ResponseEntity.noContent().build();
    }

}
