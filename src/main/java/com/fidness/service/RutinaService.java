package com.fidness.service;

import com.fidness.domain.Ejercicio;
import com.fidness.domain.Rutina;
import com.fidness.domain.Usuario;
import com.fidness.repository.EjercicioRepository;
import com.fidness.repository.RutinaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RutinaService {

    private final RutinaRepository rutinaRepository;
    private final EjercicioRepository ejercicioRepository;

    public RutinaService(
            RutinaRepository rutinaRepository,
            EjercicioRepository ejercicioRepository) {

        this.rutinaRepository = rutinaRepository;
        this.ejercicioRepository = ejercicioRepository;
    }

    @Transactional(readOnly = true)
    public List<Rutina> getRutinas(boolean activo) {

        if (activo) {
            return rutinaRepository.findByActivoTrue();
        }

        return rutinaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Rutina> getRutinasUsuario(
            Long idUsuario) {

        return rutinaRepository
                .findByUsuarioIdUsuarioOrderByIdRutinaDesc(
                        idUsuario);
    }

    @Transactional(readOnly = true)
    public Optional<Rutina> getRutina(
            Integer idRutina) {

        return rutinaRepository.findById(idRutina);
    }

    @Transactional(readOnly = true)
    public Optional<Rutina> getRutinaUsuario(
            Integer idRutina,
            Long idUsuario) {

        return rutinaRepository
                .findByIdRutinaAndUsuarioIdUsuario(
                        idRutina,
                        idUsuario);
    }

    @Transactional
    public Rutina save(
            Rutina rutina,
            Usuario usuario) {

        rutina.setUsuario(usuario);

        return rutinaRepository.save(rutina);
    }

    @Transactional
    public void agregarEjercicio(
            Rutina rutina,
            Ejercicio ejercicio) {

        rutina.getEjercicios().add(ejercicio);

        rutinaRepository.save(rutina);
    }

    @Transactional
    public void eliminarEjercicio(
            Rutina rutina,
            Integer idEjercicio) {

        rutina.getEjercicios()
                .removeIf(
                        ejercicio ->
                        ejercicio.getIdEjercicio()
                                .equals(idEjercicio)
                );

        rutinaRepository.save(rutina);
    }

    @Transactional
    public void agregarEjercicio(
            Integer idRutina,
            Integer idEjercicio) {

        Rutina rutina =
                rutinaRepository.findById(idRutina)
                        .orElseThrow();

        Ejercicio ejercicio =
                ejercicioRepository.findById(idEjercicio)
                        .orElseThrow();

        rutina.getEjercicios().add(ejercicio);

        rutinaRepository.save(rutina);
    }

    @Transactional
    public void delete(
            Integer idRutina) {

        if (!rutinaRepository.existsById(idRutina)) {

            throw new IllegalArgumentException(
                    "La rutina con ID "
                    + idRutina
                    + " no existe."
            );
        }

        try {

            rutinaRepository.deleteById(idRutina);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "No se puede eliminar la rutina.",
                    e
            );
        }
    }
}
