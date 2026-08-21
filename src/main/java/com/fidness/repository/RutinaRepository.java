package com.fidness.repository;

import com.fidness.domain.Rutina;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaRepository
        extends JpaRepository<Rutina, Integer> {

    List<Rutina> findByActivoTrue();

    List<Rutina> findByUsuarioIdUsuarioOrderByIdRutinaDesc(
            Long idUsuario);

    Optional<Rutina> findByIdRutinaAndUsuarioIdUsuario(
            Integer idRutina,
            Long idUsuario);

    @Modifying
    @Query(
            value = """
                    DELETE FROM rutina_ejercicio
                    WHERE id_ejercicio = :idEjercicio
                    """,
            nativeQuery = true
    )
    void eliminarRelacionesPorEjercicio(
            @Param("idEjercicio")
            Integer idEjercicio);
}