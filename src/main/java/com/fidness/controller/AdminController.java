package com.fidness.controller;

import com.fidness.service.ClaseGrupalService;
import com.fidness.service.EjercicioService;
import com.fidness.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EjercicioService ejercicioService;
    private final ClaseGrupalService claseGrupalService;
    private final UsuarioService usuarioService;

    public AdminController(
            EjercicioService ejercicioService,
            ClaseGrupalService claseGrupalService,
            UsuarioService usuarioService) {

        this.ejercicioService = ejercicioService;
        this.claseGrupalService = claseGrupalService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String inicio(Model model) {

        model.addAttribute(
                "totalEjercicios",
                ejercicioService
                        .getEjercicios(false)
                        .size());

        model.addAttribute(
                "totalClases",
                claseGrupalService
                        .getTodas()
                        .size());

        model.addAttribute(
                "totalUsuarios",
                usuarioService
                        .getUsuarios()
                        .size());

        return "/admin/index";
    }
}
