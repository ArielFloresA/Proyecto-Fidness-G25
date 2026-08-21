package com.fidness.service;

import com.fidness.domain.ClaseGrupal;
import com.fidness.repository.ClaseGrupalRepository;
import com.fidness.repository.ReservaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClaseGrupalService {

    private final ClaseGrupalRepository claseGrupalRepository;
    private final ReservaRepository reservaRepository;

    public ClaseGrupalService(
            ClaseGrupalRepository claseGrupalRepository,
            ReservaRepository reservaRepository) {

        this.claseGrupalRepository = claseGrupalRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional(readOnly = true)
    public List<ClaseGrupal> getClasesActivas() {

        return claseGrupalRepository
                .findByActivoTrueOrderByFechaAscHoraAsc();
    }

    @Transactional(readOnly = true)
    public List<ClaseGrupal> getTodas() {

        return claseGrupalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ClaseGrupal> getClase(
            Integer idClase) {

        return claseGrupalRepository
                .findById(idClase);
    }

    @Transactional(readOnly = true)
    public long getReservados(
            Integer idClase) {

        return reservaRepository
                .countByClaseIdClaseAndActivoTrue(
                        idClase);
    }

    @Transactional(readOnly = true)
    public int getDisponibles(
            ClaseGrupal clase) {

        long reservados =
                getReservados(
                        clase.getIdClase());

        return Math.max(
                0,
                clase.getCapacidad()
                - (int) reservados
        );
    }

    @Transactional
    public void save(
            ClaseGrupal clase) {

        claseGrupalRepository.save(clase);
    }

    @Transactional
    public void delete(
            Integer idClase) {

        claseGrupalRepository.deleteById(
                idClase);
    }
}
