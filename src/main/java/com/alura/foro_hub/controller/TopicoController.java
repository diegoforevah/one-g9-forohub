package com.alura.foro_hub.controller;

import com.alura.foro_hub.domain.topico.*;
import com.alura.foro_hub.domain.usuario.Usuario;
import com.alura.foro_hub.infra.errors.ValidacionEx;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    private final TopicoRepo repository;

    public TopicoController(TopicoRepo repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoListadoDto> listarTopico(@PathVariable Long id, Authentication auth) {
        boolean usuarioEsAdmin = getUsuarioEsAdmin(auth);
        Topico topico = usuarioEsAdmin
                ? repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado."))
                : repository.findByIdAndStatusTrue(id).orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado."));

        return ResponseEntity.ok(new TopicoListadoDto(topico));
    }

    @GetMapping
    public ResponseEntity<Page<TopicoListadoDto>> listarTopicos(
            @PageableDefault(sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer year,
            Authentication auth) {
        boolean usuarioEsAdmin = getUsuarioEsAdmin(auth);
        Page<Topico> pagina;

        if (curso != null && year != null) {
            pagina = usuarioEsAdmin
                    ? repository.findByCursoAndYear(curso, year, paginacion)
                    : repository.findByCursoAndYearAndStatusTrue(curso, year, paginacion);
        } else {
            pagina = usuarioEsAdmin
                    ? repository.findAll(paginacion)
                    : repository.findAllByStatusTrue(paginacion);
        }
        return ResponseEntity.ok(pagina.map(TopicoListadoDto::new));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoListadoDto> registrarTopico(@RequestBody @Valid TopicoDto datos, Authentication auth) {
        if (repository.existsByTituloAndMensajeAndStatusTrue(datos.titulo(), datos.mensaje())) {
            throw new ValidacionEx("Ya existe un tópico con el mismo título y mensaje.");
        }

        Usuario usuario = (Usuario) auth.getPrincipal();
        Topico topico = new Topico(datos, usuario);
        repository.save(topico);

        return ResponseEntity.ok(new TopicoListadoDto(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TopicoListadoDto> actualizarTopico(@RequestBody @Valid TopicoActualizarDto datos, Authentication auth) {
        boolean usuarioEsAdmin = getUsuarioEsAdmin(auth);

        Topico topico = usuarioEsAdmin
                ? repository.findById(datos.id()).orElseThrow(() -> new EntityNotFoundException("El tópico no existe."))
                : repository.findByIdAndStatusTrue(datos.id()).orElseThrow(() -> new EntityNotFoundException("El tópico no existe."));

        Usuario usuarioLogueado = (Usuario) auth.getPrincipal();

        assert usuarioLogueado != null;
        if (!usuarioEsAdmin && !topico.getAutor().getId().equals(usuarioLogueado.getId())) {
            throw new AccessDeniedException("No tienes permisos para editar este tópico.");
        }

        topico.actualizar(datos);

        return ResponseEntity.ok(new TopicoListadoDto(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id, Authentication auth) {
        boolean usuarioEsAdmin = getUsuarioEsAdmin(auth);

        Topico topico = usuarioEsAdmin
                ? repository.findById(id).orElseThrow(() -> new EntityNotFoundException("El tópico no existe."))
                : repository.findByIdAndStatusTrue(id).orElseThrow(() -> new EntityNotFoundException("El tópico no existe."));

        Usuario usuarioLogueado = (Usuario) auth.getPrincipal();

        assert usuarioLogueado != null;
        if (!usuarioEsAdmin && !topico.getAutor().getId().equals(usuarioLogueado.getId())) {
            throw new AccessDeniedException("No tienes permisos para eliminar este tópico.");
        }

        topico.desactivar();

        return ResponseEntity.noContent().build();
    }

    private boolean getUsuarioEsAdmin(Authentication auth) {
        Usuario usuario = (Usuario) auth.getPrincipal();
        assert usuario != null;
        return usuario.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"));
    }
}