package com.fidness.service;

import com.fidness.domain.Rol;
import com.fidness.domain.Rutina;
import com.fidness.domain.Usuario;
import com.fidness.repository.ProgresoRepository;
import com.fidness.repository.ReservaRepository;
import com.fidness.repository.RolRepository;
import com.fidness.repository.RutinaRepository;
import com.fidness.repository.UsuarioRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RutinaRepository rutinaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ProgresoRepository progresoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario getUsuario(Long idUsuario) {

        return usuarioRepository
                .findById(idUsuario)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsername(
            String username) {

        Optional<Usuario> usuario =
                usuarioRepository
                        .findByUsername(username);

        return usuario.orElse(null);
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioPorCorreo(
            String correo) {

        Optional<Usuario> usuario =
                usuarioRepository
                        .findByCorreo(correo);

        return usuario.orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean existeUsername(
            String username) {

        return usuarioRepository
                .existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existeCorreo(
            String correo) {

        return usuarioRepository
                .existsByCorreo(correo);
    }

    @Transactional
    public Usuario registrarUsuario(
            Usuario usuario) {

        usuario.setPassword(
                passwordEncoder.encode(
                        usuario.getPassword()
                )
        );

        usuario.setActivo(true);

        Usuario usuarioGuardado =
                usuarioRepository.save(usuario);

        crearRolUsuario(usuarioGuardado);

        return usuarioGuardado;
    }

    @Transactional
    public Usuario registrarUsuarioInactivo(
            Usuario usuario) {

        usuario.setPassword(
                passwordEncoder.encode(
                        usuario.getPassword()
                )
        );

        usuario.setActivo(false);

        Usuario usuarioGuardado =
                usuarioRepository.save(usuario);

        crearRolUsuario(usuarioGuardado);

        return usuarioGuardado;
    }

    private void crearRolUsuario(
            Usuario usuarioGuardado) {

        boolean tieneRol =
                rolRepository
                        .existsByUsuarioIdUsuarioAndNombre(
                                usuarioGuardado
                                        .getIdUsuario(),
                                "ROLE_USER"
                        );

        if (!tieneRol) {

            Rol rol = new Rol();

            rol.setNombre("ROLE_USER");
            rol.setUsuario(usuarioGuardado);

            rolRepository.save(rol);
        }
    }

    @Transactional
    public void guardarUsuario(
            Usuario usuario) {

        usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizarPerfil(
            Usuario usuarioActual,
            String nombre,
            String apellidos,
            String correo,
            String telefono,
            MultipartFile imagenFile) {

        usuarioActual.setNombre(nombre);
        usuarioActual.setApellidos(apellidos);
        usuarioActual.setCorreo(correo);
        usuarioActual.setTelefono(telefono);

        Usuario usuarioGuardado =
                usuarioRepository
                        .save(usuarioActual);

        if (imagenFile != null
                && !imagenFile.isEmpty()) {

            try {

                String rutaImagen =
                        firebaseStorageService
                                .uploadImage(
                                        imagenFile,
                                        "usuarios",
                                        usuarioGuardado
                                                .getIdUsuario()
                                                .intValue()
                                );

                usuarioGuardado
                        .setRutaImagen(rutaImagen);

                usuarioGuardado =
                        usuarioRepository
                                .save(usuarioGuardado);

            } catch (IOException e) {

                throw new RuntimeException(
                        "No fue posible guardar la imagen de perfil.",
                        e
                );
            }
        }

        return usuarioGuardado;
    }

    @Transactional(readOnly = true)
    public boolean tieneDatosRelacionados(
            Long idUsuario) {

        Integer resultado =
                usuarioRepository
                        .tieneDatosRelacionados(
                                idUsuario
                        );

        return resultado != null
                && resultado == 1;
    }

    @Transactional
    public boolean eliminarUsuario(
            Long idUsuario) {

        Usuario usuario =
                usuarioRepository
                        .findById(idUsuario)
                        .orElse(null);

        if (usuario == null) {
            return false;
        }

        /*
         * 1. Eliminar reservas del usuario.
         */
        var reservas =
                reservaRepository
                        .findByUsuarioIdUsuario(
                                idUsuario
                        );

        if (!reservas.isEmpty()) {

            reservaRepository.deleteAll(
                    reservas
            );
        }

        /*
         * 2. Eliminar registros de progreso.
         */
        var progresos =
                progresoRepository
                        .findByUsuarioIdUsuario(
                                idUsuario
                        );

        if (!progresos.isEmpty()) {

            progresoRepository.deleteAll(
                    progresos
            );
        }

        /*
         * 3. Eliminar rutinas.
         *
         * Primero vaciamos la relación
         * rutina_ejercicio para evitar
         * conflictos con la tabla intermedia.
         */
        List<Rutina> rutinas =
                rutinaRepository
                        .findByUsuarioIdUsuarioOrderByIdRutinaDesc(
                                idUsuario
                        );

        for (Rutina rutina : rutinas) {

            rutina.getEjercicios().clear();

            rutinaRepository.save(
                    rutina
            );
        }

        if (!rutinas.isEmpty()) {

            rutinaRepository.deleteAll(
                    rutinas
            );
        }

        /*
         * 4. Eliminar roles.
         */
        var roles =
                rolRepository
                        .findByUsuarioIdUsuario(
                                idUsuario
                        );

        if (!roles.isEmpty()) {

            rolRepository.deleteAll(
                    roles
            );
        }

        /*
         * 5. Finalmente eliminar usuario.
         */
        usuarioRepository.delete(
                usuario
        );

        return true;
    }

    @Transactional
    public void actualizarPassword(
            Usuario usuario,
            String nuevaPassword) {

        usuario.setPassword(
                passwordEncoder.encode(
                        nuevaPassword
                )
        );

        usuarioRepository.save(usuario);
    }

    @Transactional
    public void cambiarEstado(
            Long idUsuario) {

        Usuario usuario =
                usuarioRepository
                        .findById(idUsuario)
                        .orElse(null);

        if (usuario == null) {
            return;
        }

        usuario.setActivo(
                !usuario.isActivo()
        );

        usuarioRepository.save(usuario);
    }
}
