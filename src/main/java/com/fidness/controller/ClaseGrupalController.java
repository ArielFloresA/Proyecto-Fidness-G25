package com.fidness.controller;

import com.fidness.domain.ClaseGrupal;
import com.fidness.domain.Usuario;
import com.fidness.service.ClaseGrupalService;
import com.fidness.service.ReservaService;
import com.fidness.service.UsuarioService;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clase")
public class ClaseGrupalController {

    private final ClaseGrupalService claseGrupalService;
    private final ReservaService reservaService;
    private final UsuarioService usuarioService;

    public ClaseGrupalController(
            ClaseGrupalService claseGrupalService,
            ReservaService reservaService,
            UsuarioService usuarioService) {

        this.claseGrupalService =
                claseGrupalService;

        this.reservaService =
                reservaService;

        this.usuarioService =
                usuarioService;
    }

    @GetMapping("/listado")
    public String listado(
            Principal principal,
            Model model) {

        var clases =
                claseGrupalService
                        .getClasesActivas();

        Map<Integer, Integer> cuposDisponibles =
                new HashMap<>();

        for (ClaseGrupal clase : clases) {

            cuposDisponibles.put(
                    clase.getIdClase(),
                    claseGrupalService
                            .getDisponibles(clase)
            );
        }

        model.addAttribute(
                "clases",
                clases
        );

        model.addAttribute(
                "cuposDisponibles",
                cuposDisponibles
        );

        if (principal != null) {

            Usuario usuario =
                    usuarioService
                            .getUsuarioPorCorreo(
                                    principal.getName()
                            );

            model.addAttribute(
                    "usuario",
                    usuario
            );
        }

        return "/clase/listado";
    }

    @GetMapping("/reservar/{idClase}")
    public String reservar(
            @PathVariable Integer idClase,
            Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario =
                usuarioService
                        .getUsuarioPorCorreo(
                                principal.getName()
                        );

        var clase =
                claseGrupalService
                        .getClase(idClase);

        if (usuario == null
                || clase.isEmpty()) {

            return "redirect:/clase/listado";
        }

        boolean reservado =
                reservaService.reservar(
                        usuario,
                        clase.get()
                );

        if (reservado) {

            return "redirect:/clase/listado?reservado";
        }

        return "redirect:/clase/listado?sinCupo";
    }
}
