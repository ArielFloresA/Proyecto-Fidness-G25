package com.fidness.controller;

import com.fidness.service.MembresiaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/membresia")
public class MembresiaController {

    private final MembresiaService membresiaService;

    public MembresiaController(
            MembresiaService membresiaService) {

        this.membresiaService =
                membresiaService;
    }

    @GetMapping("/listado")
    public String listado(
            Model model) {

        model.addAttribute(
                "membresias",
                membresiaService
                        .getMembresias(true)
        );

        return "/membresia/listado";
    }
}
