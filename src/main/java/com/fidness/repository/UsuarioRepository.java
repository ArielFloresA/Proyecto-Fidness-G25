package com.fidness.repository;

import com.fidness.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByUsername(String username);

    boolean existsByCorreo(String correo);

    @Query(
            value = """
                    SELECT CASE
                        WHEN EXISTS (
                            SELECT 1
                            FROM rutina
                            WHERE id_usuario = :idUsuario
                        )
                        OR EXISTS (
                            SELECT 1
                            FROM reserva
                            WHERE id_usuario = :idUsuario
                        )
                        OR EXISTS (
                            SELECT 1
                            FROM progreso
                            WHERE id_usuario = :idUsuario
                        )
                        THEN 1
                        ELSE 0
                    END
                    """,
            nativeQuery = true
    )
    Integer tieneDatosRelacionados(
            @Param("idUsuario") Long idUsuario
    );
}