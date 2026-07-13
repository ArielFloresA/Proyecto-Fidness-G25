package com.fidness.controller;

import com.fidness.domain.Ejercicio;
import com.fidness.service.EjercicioService;
import com.fidness.service.RutinaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ejercicio")
public class EjercicioController {

    private final EjercicioService ejercicioService;
    private final RutinaService rutinaService;

    public EjercicioController(EjercicioService ejercicioService,
            RutinaService rutinaService) {
        this.ejercicioService = ejercicioService;
        this.rutinaService = rutinaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {

        var ejercicios = ejercicioService.getEjercicios(true);

        model.addAttribute("ejercicios", ejercicios);

        return "/ejercicio/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Ejercicio ejercicio) {
        return "/ejercicio/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Ejercicio ejercicio,
            MultipartFile imagenFile) {

        ejercicioService.save(ejercicio, imagenFile);

        return "redirect:/ejercicio/listado";
    }

    @GetMapping("/agregarRutina/{idEjercicio}")
    public String agregarRutina(@PathVariable Integer idEjercicio,
            Model model) {

        var ejercicio = ejercicioService.getEjercicio(idEjercicio);

        if (ejercicio.isPresent()) {

            model.addAttribute("ejercicio", ejercicio.get());

            model.addAttribute("rutinas",
                    rutinaService.getRutinas(true));

            return "/ejercicio/agregarRutina";

        }

        return "redirect:/ejercicio/listado";

    }

}
