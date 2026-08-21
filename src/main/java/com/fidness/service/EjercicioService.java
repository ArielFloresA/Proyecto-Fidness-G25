package com.fidness.service;

import com.fidness.domain.Ejercicio;
import com.fidness.repository.EjercicioRepository;
import com.fidness.repository.RutinaRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;
    private final RutinaRepository rutinaRepository;
    private final FirebaseStorageService firebaseStorageService;

    public EjercicioService(
            EjercicioRepository ejercicioRepository,
            RutinaRepository rutinaRepository,
            FirebaseStorageService firebaseStorageService) {

        this.ejercicioRepository = ejercicioRepository;
        this.rutinaRepository = rutinaRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional(readOnly = true)
    public List<Ejercicio> getEjercicios(boolean activo) {

        if (activo) {
            return ejercicioRepository.findByActivoTrue();
        }

        return ejercicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Ejercicio> getEjercicio(
            Integer idEjercicio) {

        return ejercicioRepository.findById(idEjercicio);
    }

    @Transactional(readOnly = true)
    public List<Ejercicio> filtrarEjercicios(
            String grupoMuscular,
            String nivel,
            String tipoEntrenamiento) {

        boolean tieneGrupo =
                grupoMuscular != null
                && !grupoMuscular.isBlank();

        boolean tieneNivel =
                nivel != null
                && !nivel.isBlank();

        boolean tieneTipo =
                tipoEntrenamiento != null
                && !tipoEntrenamiento.isBlank();

        if (tieneGrupo && tieneNivel && tieneTipo) {

            return ejercicioRepository
                    .findByActivoTrueAndGrupoMuscularIgnoreCaseAndNivelIgnoreCaseAndTipoEntrenamientoIgnoreCase(
                            grupoMuscular,
                            nivel,
                            tipoEntrenamiento);
        }

        if (tieneGrupo && tieneNivel) {

            return ejercicioRepository
                    .findByActivoTrueAndGrupoMuscularIgnoreCaseAndNivelIgnoreCase(
                            grupoMuscular,
                            nivel);
        }

        if (tieneGrupo && tieneTipo) {

            return ejercicioRepository
                    .findByActivoTrueAndGrupoMuscularIgnoreCaseAndTipoEntrenamientoIgnoreCase(
                            grupoMuscular,
                            tipoEntrenamiento);
        }

        if (tieneNivel && tieneTipo) {

            return ejercicioRepository
                    .findByActivoTrueAndNivelIgnoreCaseAndTipoEntrenamientoIgnoreCase(
                            nivel,
                            tipoEntrenamiento);
        }

        if (tieneGrupo) {

            return ejercicioRepository
                    .findByActivoTrueAndGrupoMuscularIgnoreCase(
                            grupoMuscular);
        }

        if (tieneNivel) {

            return ejercicioRepository
                    .findByActivoTrueAndNivelIgnoreCase(
                            nivel);
        }

        if (tieneTipo) {

            return ejercicioRepository
                    .findByActivoTrueAndTipoEntrenamientoIgnoreCase(
                            tipoEntrenamiento);
        }

        return ejercicioRepository.findByActivoTrue();
    }

    @Transactional
    public void save(
            Ejercicio ejercicio,
            MultipartFile imagenFile) {

        ejercicioRepository.save(ejercicio);

        if (imagenFile != null
                && !imagenFile.isEmpty()) {

            try {

                String rutaImagen =
                        firebaseStorageService.uploadImage(
                                imagenFile,
                                "ejercicios",
                                ejercicio.getIdEjercicio());

                ejercicio.setImagen(rutaImagen);

                ejercicioRepository.save(ejercicio);

            } catch (IOException ex) {

                throw new RuntimeException(
                        "Error al subir la imagen a Firebase",
                        ex);
            }
        }
    }

    @Transactional
    public void delete(Integer idEjercicio) {

        if (!ejercicioRepository.existsById(idEjercicio)) {

            throw new IllegalArgumentException(
                    "El ejercicio con ID "
                    + idEjercicio
                    + " no existe.");
        }

        try {

            /*
             * Primero eliminamos las relaciones del ejercicio
             * con las rutinas.
             */
            rutinaRepository
                    .eliminarRelacionesPorEjercicio(
                            idEjercicio);

            /*
             * Después eliminamos el ejercicio.
             */
            ejercicioRepository
                    .deleteById(idEjercicio);

            ejercicioRepository.flush();

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "No se puede eliminar el ejercicio porque tiene información asociada.",
                    e);
        }
    }
}
