package com.fidness.repository;

import com.fidness.domain.Progreso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgresoRepository
        extends JpaRepository<Progreso, Integer> {

    List<Progreso>
            findByUsuarioIdUsuarioOrderByFechaDesc(
                    Long idUsuario);

    List<Progreso>
            findByUsuarioIdUsuario(
                    Long idUsuario);
}
