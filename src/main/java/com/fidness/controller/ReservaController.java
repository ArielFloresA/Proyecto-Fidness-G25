package com.fidness.controller;

import com.fidness.domain.Usuario;
import com.fidness.service.ReservaService;
import com.fidness.service.UsuarioService;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;

    public ReservaController(
            ReservaService reservaService,
            UsuarioService usuarioService) {

        this.reservaService =
                reservaService;

        this.usuarioService =
                usuarioService;
    }

    @GetMapping("/misReservas")
    public String misReservas(
            Principal principal,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario =
                usuarioService
                        .getUsuarioPorCorreo(
                                principal.getName());

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "reservas",
                reservaService
                        .getReservasUsuario(
                                usuario.getIdUsuario()));

        return "/reserva/listado";
    }

    @GetMapping("/cancelar/{idReserva}")
    public String cancelar(
            @PathVariable Integer idReserva,
            Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario =
                usuarioService
                        .getUsuarioPorCorreo(
                                principal.getName());

        if (usuario != null) {

            reservaService.cancelar(
                    idReserva,
                    usuario.getIdUsuario());
        }

        return "redirect:/reserva/misReservas?cancelada";
    }
}
