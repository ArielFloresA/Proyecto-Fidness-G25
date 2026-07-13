package com.fidness.service;

import com.fidness.domain.Ejercicio;
import com.fidness.repository.EjercicioRepository;
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
    private final FirebaseStorageService firebaseStorageService;

    public EjercicioService(EjercicioRepository ejercicioRepository,
            FirebaseStorageService firebaseStorageService) {
        this.ejercicioRepository = ejercicioRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional(readOnly = true)
    public List<Ejercicio> getEjercicios(boolean activo) {
        if (activo) {
            return ejercicioRepository.findByActivoTrue();
        } else {
            return ejercicioRepository.findAll();
        }
    }

    @Transactional(readOnly = true)
    public Optional<Ejercicio> getEjercicio(Integer idEjercicio) {
        return ejercicioRepository.findById(idEjercicio);
    }

    @Transactional
    public void save(Ejercicio ejercicio,
            MultipartFile imagenFile) {

        ejercicioRepository.save(ejercicio);

        if (imagenFile != null && !imagenFile.isEmpty()) {

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
                    "El ejercicio con ID " + idEjercicio + " no existe.");
        }

        try {

            ejercicioRepository.deleteById(idEjercicio);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "No se puede eliminar el ejercicio porque tiene información asociada.",
                    e);

        }

    }

}
