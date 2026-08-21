package com.fidness.controller;

import com.fidness.domain.Progreso;
import com.fidness.domain.Usuario;
import com.fidness.service.ProgresoService;
import com.fidness.service.UsuarioService;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/progreso")
public class ProgresoController {

    private final ProgresoService progresoService;
    private final UsuarioService usuarioService;

    public ProgresoController(
            ProgresoService progresoService,
            UsuarioService usuarioService) {

        this.progresoService =
                progresoService;

        this.usuarioService =
                usuarioService;
    }

    private Usuario getUsuario(
            Principal principal) {

        if (principal == null) {
            return null;
        }

        return usuarioService
                .getUsuarioPorCorreo(
                        principal.getName());
    }

    @GetMapping("/listado")
    public String listado(
            Principal principal,
            Model model) {

        Usuario usuario =
                getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        var progresos =
                progresoService
                        .getProgresosUsuario(
                                usuario.getIdUsuario());

        model.addAttribute(
                "progresos",
                progresos);

        model.addAttribute(
                "usuario",
                usuario);

        return "/progreso/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(
            Principal principal,
            Model model) {

        Usuario usuario =
                getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        Progreso progreso =
                new Progreso();

        progreso.setFecha(
                LocalDate.now());

        model.addAttribute(
                "progreso",
                progreso);

        return "/progreso/nuevo";
    }

    @PostMapping("/guardar")
    public String guardar(
            Progreso progreso,
            Principal principal) {

        Usuario usuario =
                getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        progresoService.save(
                progreso,
                usuario);

        return "redirect:/progreso/listado?guardado";
    }

    @GetMapping("/eliminar/{idProgreso}")
    public String eliminar(
            @PathVariable Integer idProgreso,
            Principal principal) {

        Usuario usuario =
                getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        progresoService.delete(
                idProgreso,
                usuario.getIdUsuario());

        return "redirect:/progreso/listado?eliminado";
    }
}
