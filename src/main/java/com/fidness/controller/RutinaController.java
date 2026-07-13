package com.fidness.controller;

import com.fidness.domain.Rutina;
import com.fidness.service.EjercicioService;
import com.fidness.service.RutinaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rutina")
public class RutinaController {

    private final RutinaService rutinaService;
    private final EjercicioService ejercicioService;

    public RutinaController(RutinaService rutinaService,
            EjercicioService ejercicioService) {
        this.rutinaService = rutinaService;
        this.ejercicioService = ejercicioService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {

        var rutinas = rutinaService.getRutinas(true);

        model.addAttribute("rutinas", rutinas);

        return "/rutina/listado";

    }

    @GetMapping("/nuevo")
    public String nuevo(Rutina rutina) {
        return "/rutina/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Rutina rutina) {

        rutinaService.save(rutina);

        return "redirect:/rutina/listado";

    }

    @GetMapping("/detalle/{idRutina}")
    public String detalle(@PathVariable Integer idRutina,
            Model model) {

        var rutina = rutinaService.getRutina(idRutina);

        if (rutina.isPresent()) {

            model.addAttribute("rutina", rutina.get());

            model.addAttribute("ejercicios",
                    ejercicioService.getEjercicios(true));

            return "/rutina/detalle";
        }

        return "redirect:/rutina/listado";
    }

}
