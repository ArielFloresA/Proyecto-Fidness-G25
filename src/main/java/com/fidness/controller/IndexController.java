package com.fidness.controller;

import com.fidness.service.EjercicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final EjercicioService ejercicioService;

    public IndexController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping("/")
    public String inicio(Model model) {

        var ejercicios = ejercicioService.getEjercicios(true);

        model.addAttribute("ejercicios", ejercicios);

        return "/index";
    }

}
