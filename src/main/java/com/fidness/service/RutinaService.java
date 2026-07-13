package com.fidness.service;

import com.fidness.domain.Rutina;
import com.fidness.repository.RutinaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RutinaService {

    private final RutinaRepository rutinaRepository;

    public RutinaService(RutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
    }

    @Transactional(readOnly = true)
    public List<Rutina> getRutinas(boolean activo) {

        if (activo) {
            return rutinaRepository.findByActivoTrue();
        } else {
            return rutinaRepository.findAll();
        }

    }

    @Transactional(readOnly = true)
    public Optional<Rutina> getRutina(Integer idRutina) {
        return rutinaRepository.findById(idRutina);
    }

    @Transactional
    public void save(Rutina rutina) {
        rutinaRepository.save(rutina);
    }

    @Transactional
    public void delete(Integer idRutina) {

        if (!rutinaRepository.existsById(idRutina)) {

            throw new IllegalArgumentException(
                    "La rutina con ID " + idRutina + " no existe.");

        }

        try {

            rutinaRepository.deleteById(idRutina);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "No se puede eliminar la rutina.",
                    e);

        }

    }

}
