package com.fidness.repository;

import com.fidness.domain.Reserva;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository
        extends JpaRepository<Reserva, Integer> {

    List<Reserva>
            findByUsuarioIdUsuarioAndActivoTrueOrderByClaseFechaAsc(
                    Long idUsuario);

    List<Reserva>
            findByUsuarioIdUsuario(
                    Long idUsuario);

    List<Reserva>
            findByClaseIdClaseAndActivoTrue(
                    Integer idClase);

    Optional<Reserva>
            findByUsuarioIdUsuarioAndClaseIdClase(
                    Long idUsuario,
                    Integer idClase);

    long countByClaseIdClaseAndActivoTrue(
            Integer idClase);
}