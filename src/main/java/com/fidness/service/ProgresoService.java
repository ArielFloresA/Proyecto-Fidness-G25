package com.fidness.service;

import com.fidness.domain.Progreso;
import com.fidness.domain.Usuario;
import com.fidness.repository.ProgresoRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgresoService {

    private final ProgresoRepository progresoRepository;

    public ProgresoService(
            ProgresoRepository progresoRepository) {

        this.progresoRepository =
                progresoRepository;
    }

    @Transactional(readOnly = true)
    public List<Progreso> getProgresosUsuario(
            Long idUsuario) {

        return progresoRepository
                .findByUsuarioIdUsuarioOrderByFechaDesc(
                        idUsuario);
    }

    @Transactional
    public void save(
            Progreso progreso,
            Usuario usuario) {

        progreso.setUsuario(usuario);

        if (progreso.getFecha() == null) {
            progreso.setFecha(LocalDate.now());
        }

        progresoRepository.save(progreso);
    }

    @Transactional
    public void delete(
            Integer idProgreso,
            Long idUsuario) {

        var progreso =
                progresoRepository.findById(idProgreso);

        if (progreso.isEmpty()) {
            return;
        }

        if (!progreso.get()
                .getUsuario()
                .getIdUsuario()
                .equals(idUsuario)) {

            return;
        }

        progresoRepository.delete(
                progreso.get());
    }
}
