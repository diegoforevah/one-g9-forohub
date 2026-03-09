package com.alura.foro_hub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepo extends JpaRepository<Topico, Long> {
    Page<Topico> findAllByStatusTrue(Pageable paginacion);
    Optional<Topico> findByIdAndStatusTrue(Long id);
    boolean existsByTituloAndMensajeAndStatusTrue(String titulo, String mensaje);
    @Query("""            
             SELECT t FROM Topico t
             WHERE t.status = true
             AND LOWER(t.curso) LIKE LOWER(CONCAT('%', :nombreCurso, '%'))
             AND YEAR(t.fechaCreacion) = :year
            """)
    Page<Topico> findByCursoAndYearAndStatusTrue(String nombreCurso, int year, Pageable paginacion);
    @Query("""            
             SELECT t FROM Topico t
             WHERE
             LOWER(t.curso) LIKE LOWER(CONCAT('%', :nombreCurso, '%'))
             AND YEAR(t.fechaCreacion) = :year
            """)
    Page<Topico> findByCursoAndYear(String nombreCurso, int year, Pageable paginacion);
}
