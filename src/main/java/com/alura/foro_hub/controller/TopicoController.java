package com.alura.foro_hub.controller;

import com.alura.foro_hub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepo repository;

    @GetMapping("/{id}")
    public ResponseEntity<TopicoListadoDto> listarTopico(@PathVariable Long id) {
        Topico topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new TopicoListadoDto(topico));
    }

    @GetMapping
    public ResponseEntity<Page<TopicoListadoDto>> listarTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        return ResponseEntity.ok(repository.findAll(paginacion).map(TopicoListadoDto::new));
    }

    @PostMapping
    @Transactional
    public void registrarTopico(@RequestBody @Valid TopicoDto datos) {
        repository.save(new Topico(datos));
        System.out.println("Tópico registrado con éxito: " + datos.titulo());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TopicoListadoDto> actualizarTopico(@RequestBody @Valid TopicoActualizarDto datos) {
        Topico topico = repository.getReferenceById(datos.id());
        topico.actualizar(datos);
        return ResponseEntity.ok(new TopicoListadoDto(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}