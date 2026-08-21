package com.fidness.repository;

import com.fidness.domain.Rol;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    List<Rol> findByUsuarioIdUsuario(Long idUsuario);

    Optional<Rol> findByUsuarioIdUsuarioAndNombre(
            Long idUsuario,
            String nombre
    );

    boolean existsByUsuarioIdUsuarioAndNombre(
            Long idUsuario,
            String nombre
    );
}
