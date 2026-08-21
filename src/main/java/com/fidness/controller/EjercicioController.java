package com.fidness.controller;

import com.fidness.domain.Ejercicio;
import com.fidness.domain.Usuario;
import com.fidness.service.EjercicioService;
import com.fidness.service.RutinaService;
import com.fidness.service.UsuarioService;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ejercicio")
public class EjercicioController {

    private final EjercicioService ejercicioService;
    private final RutinaService rutinaService;
    private final UsuarioService usuarioService;

    public EjercicioController(
            EjercicioService ejercicioService,
            RutinaService rutinaService,
            UsuarioService usuarioService) {

        this.ejercicioService = ejercicioService;
        this.rutinaService = rutinaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listado")
    public String listado(
            @RequestParam(required = false) String grupoMuscular,
            @RequestParam(required = false) String nivel,
            @RequestParam(required = false) String tipoEntrenamiento,
            Model model) {

        var ejercicios =
                ejercicioService.filtrarEjercicios(
                        grupoMuscular,
                        nivel,
                        tipoEntrenamiento);

        model.addAttribute("ejercicios", ejercicios);
        model.addAttribute("grupoMuscular", grupoMuscular);
        model.addAttribute("nivel", nivel);
        model.addAttribute("tipoEntrenamiento", tipoEntrenamiento);

        return "/ejercicio/listado";
    }

    @GetMapping("/detalle/{idEjercicio}")
    public String detalle(
            @PathVariable Integer idEjercicio,
            Model model) {

        var ejercicio =
                ejercicioService.getEjercicio(idEjercicio);

        if (ejercicio.isEmpty()) {
            return "redirect:/ejercicio/listado";
        }

        model.addAttribute("ejercicio", ejercicio.get());

        return "/ejercicio/detalle";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        Ejercicio ejercicio = new Ejercicio();

        ejercicio.setActivo(true);

        model.addAttribute("ejercicio", ejercicio);

        return "/ejercicio/modifica";
    }

    @GetMapping("/editar/{idEjercicio}")
    public String editar(
            @PathVariable Integer idEjercicio,
            Model model) {

        var ejercicio =
                ejercicioService.getEjercicio(idEjercicio);

        if (ejercicio.isEmpty()) {
            return "redirect:/ejercicio/listado";
        }

        model.addAttribute("ejercicio", ejercicio.get());

        return "/ejercicio/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(
            Ejercicio ejercicio,
            MultipartFile imagenFile) {

        if (ejercicio.getIdEjercicio() != null) {

            var anterior =
                    ejercicioService.getEjercicio(
                            ejercicio.getIdEjercicio());

            if (anterior.isPresent()
                    && (imagenFile == null
                    || imagenFile.isEmpty())) {

                ejercicio.setImagen(
                        anterior.get().getImagen());
            }
        }

        ejercicioService.save(
                ejercicio,
                imagenFile);

        return "redirect:/ejercicio/listado";
    }

    @GetMapping("/eliminar/{idEjercicio}")
    public String eliminar(
            @PathVariable Integer idEjercicio) {

        try {

            ejercicioService.delete(idEjercicio);

        } catch (IllegalStateException e) {

            return "redirect:/ejercicio/listado?noEliminar";
        }

        return "redirect:/ejercicio/listado?eliminado";
    }

    @GetMapping("/agregarRutina/{idEjercicio}")
    public String agregarRutina(
            @PathVariable Integer idEjercicio,
            Principal principal,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario =
                usuarioService.getUsuarioPorCorreo(
                        principal.getName());

        if (usuario == null) {
            return "redirect:/login";
        }

        var ejercicio =
                ejercicioService.getEjercicio(
                        idEjercicio);

        if (ejercicio.isPresent()) {

            model.addAttribute(
                    "ejercicio",
                    ejercicio.get());

            model.addAttribute(
                    "rutinas",
                    rutinaService.getRutinasUsuario(
                            usuario.getIdUsuario()));

            return "/ejercicio/agregarRutina";
        }

        return "redirect:/ejercicio/listado";
    }
}
