package com.fidness.service;

import com.fidness.domain.Rol;
import com.fidness.domain.Usuario;
import com.fidness.repository.RolRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public List<Rol> getRolesUsuario(Long idUsuario) {

        return rolRepository
                .findByUsuarioIdUsuario(idUsuario);
    }

    @Transactional(readOnly = true)
    public boolean tieneRol(
            Long idUsuario,
            String nombreRol) {

        return rolRepository
                .existsByUsuarioIdUsuarioAndNombre(
                        idUsuario,
                        nombreRol
                );
    }

    @Transactional
    public void agregarRol(
            Usuario usuario,
            String nombreRol) {

        if (usuario == null) {
            return;
        }

        if (!nombreRol.equals("ROLE_USER")
                && !nombreRol.equals("ROLE_ADMIN")) {
            return;
        }

        boolean existe =
                rolRepository
                        .existsByUsuarioIdUsuarioAndNombre(
                                usuario.getIdUsuario(),
                                nombreRol
                        );

        if (existe) {
            return;
        }

        Rol rol = new Rol();

        rol.setNombre(nombreRol);
        rol.setUsuario(usuario);

        rolRepository.save(rol);
    }

    @Transactional
    public void eliminarRol(
            Long idUsuario,
            String nombreRol) {

        var rol =
                rolRepository
                        .findByUsuarioIdUsuarioAndNombre(
                                idUsuario,
                                nombreRol
                        );

        rol.ifPresent(
                rolRepository::delete
        );
    }
}