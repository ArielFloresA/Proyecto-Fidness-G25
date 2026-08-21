package com.fidness.service;

import com.fidness.domain.ClaseGrupal;
import com.fidness.domain.Reserva;
import com.fidness.domain.Usuario;
import com.fidness.repository.ReservaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClaseGrupalService claseGrupalService;

    public ReservaService(
            ReservaRepository reservaRepository,
            ClaseGrupalService claseGrupalService) {

        this.reservaRepository =
                reservaRepository;

        this.claseGrupalService =
                claseGrupalService;
    }

    @Transactional(readOnly = true)
    public List<Reserva> getReservasUsuario(
            Long idUsuario) {

        return reservaRepository
                .findByUsuarioIdUsuarioAndActivoTrueOrderByClaseFechaAsc(
                        idUsuario);
    }

    @Transactional(readOnly = true)
    public boolean usuarioTieneReserva(
            Long idUsuario,
            Integer idClase) {

        var reserva =
                reservaRepository
                        .findByUsuarioIdUsuarioAndClaseIdClase(
                                idUsuario,
                                idClase);

        return reserva.isPresent()
                && reserva.get().isActivo();
    }

    @Transactional
    public boolean reservar(
            Usuario usuario,
            ClaseGrupal clase) {

        var existente =
                reservaRepository
                        .findByUsuarioIdUsuarioAndClaseIdClase(
                                usuario.getIdUsuario(),
                                clase.getIdClase());

        if (existente.isPresent()) {

            Reserva reserva =
                    existente.get();

            if (reserva.isActivo()) {
                return false;
            }

            if (claseGrupalService
                    .getDisponibles(clase) <= 0) {

                return false;
            }

            reserva.setActivo(true);
            reserva.setFechaReserva(
                    LocalDateTime.now());

            reservaRepository.save(reserva);

            return true;
        }

        if (claseGrupalService
                .getDisponibles(clase) <= 0) {

            return false;
        }

        Reserva reserva =
                new Reserva();

        reserva.setUsuario(usuario);
        reserva.setClase(clase);
        reserva.setFechaReserva(
                LocalDateTime.now());
        reserva.setActivo(true);

        reservaRepository.save(reserva);

        return true;
    }

    @Transactional
    public boolean cancelar(
            Integer idReserva,
            Long idUsuario) {

        var reservaOptional =
                reservaRepository.findById(
                        idReserva);

        if (reservaOptional.isEmpty()) {
            return false;
        }

        Reserva reserva =
                reservaOptional.get();

        if (!reserva.getUsuario()
                .getIdUsuario()
                .equals(idUsuario)) {

            return false;
        }

        reserva.setActivo(false);

        reservaRepository.save(reserva);

        return true;
    }
}
