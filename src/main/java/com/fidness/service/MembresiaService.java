package com.fidness.service;

import com.fidness.domain.Membresia;
import com.fidness.repository.MembresiaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;

    public MembresiaService(
            MembresiaRepository membresiaRepository) {

        this.membresiaRepository =
                membresiaRepository;
    }

    @Transactional(readOnly = true)
    public List<Membresia> getMembresias(
            boolean activo) {

        if (activo) {
            return membresiaRepository
                    .findByActivoTrue();
        }

        return membresiaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Membresia> getMembresia(
            Integer idMembresia) {

        return membresiaRepository
                .findById(idMembresia);
    }

    @Transactional
    public void save(
            Membresia membresia) {

        membresiaRepository.save(membresia);
    }

    @Transactional
    public void delete(
            Integer idMembresia) {

        membresiaRepository
                .deleteById(idMembresia);
    }
}
