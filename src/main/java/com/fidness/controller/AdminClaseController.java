package com.fidness.controller;

import com.fidness.domain.ClaseGrupal;
import com.fidness.service.ClaseGrupalService;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/clase")
public class AdminClaseController {

    private final ClaseGrupalService claseGrupalService;

    public AdminClaseController(
            ClaseGrupalService claseGrupalService) {

        this.claseGrupalService =
                claseGrupalService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {

        model.addAttribute(
                "clases",
                claseGrupalService.getTodas());

        return "/admin/clase/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        ClaseGrupal clase =
                new ClaseGrupal();

        clase.setFecha(
                LocalDate.now().plusDays(1));

        clase.setHora(
                LocalTime.of(18, 0));

        clase.setDuracion(60);
        clase.setCapacidad(15);
        clase.setActivo(true);

        model.addAttribute(
                "clase",
                clase);

        return "/admin/clase/modifica";
    }

    @GetMapping("/editar/{idClase}")
    public String editar(
            @PathVariable Integer idClase,
            Model model) {

        var clase =
                claseGrupalService
                        .getClase(idClase);

        if (clase.isEmpty()) {

            return "redirect:/admin/clase/listado";
        }

        model.addAttribute(
                "clase",
                clase.get());

        return "/admin/clase/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(
            ClaseGrupal clase) {

        claseGrupalService.save(clase);

        return "redirect:/admin/clase/listado?guardado";
    }

    @GetMapping("/desactivar/{idClase}")
    public String desactivar(
            @PathVariable Integer idClase) {

        var clase =
                claseGrupalService
                        .getClase(idClase);

        if (clase.isPresent()) {

            ClaseGrupal actual =
                    clase.get();

            actual.setActivo(false);

            claseGrupalService.save(actual);
        }

        return "redirect:/admin/clase/listado?desactivada";
    }
}
