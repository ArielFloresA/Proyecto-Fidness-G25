package com.fidness.controller;

import com.fidness.domain.Rutina;
import com.fidness.domain.Usuario;
import com.fidness.service.EjercicioService;
import com.fidness.service.RutinaService;
import com.fidness.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rutina")
public class RutinaController {

    private final RutinaService rutinaService;
    private final EjercicioService ejercicioService;
    private final UsuarioService usuarioService;

    public RutinaController(
            RutinaService rutinaService,
            EjercicioService ejercicioService,
            UsuarioService usuarioService) {

        this.rutinaService = rutinaService;
        this.ejercicioService = ejercicioService;
        this.usuarioService = usuarioService;
    }

    private Usuario getUsuario(
            Principal principal) {

        if (principal == null) {
            return null;
        }

        return usuarioService
                .getUsuarioPorCorreo(
                        principal.getName()
                );
    }

    @GetMapping("/listado")
    public String listado(
            Principal principal,
            Model model) {

        Usuario usuario
                = getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "rutinas",
                rutinaService.getRutinasUsuario(
                        usuario.getIdUsuario()
                )
        );

        return "/rutina/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(
            Model model) {

        Rutina rutina
                = new Rutina();

        rutina.setActivo(true);

        model.addAttribute(
                "rutina",
                rutina
        );

        return "/rutina/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(
            Rutina rutina,
            Principal principal) {

        Usuario usuario
                = getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        if (rutina.getIdRutina() != null) {

            var existente
                    = rutinaService.getRutinaUsuario(
                            rutina.getIdRutina(),
                            usuario.getIdUsuario()
                    );

            if (existente.isEmpty()) {
                return "redirect:/rutina/listado";
            }

            Rutina actual
                    = existente.get();

            actual.setNombre(
                    rutina.getNombre()
            );

            actual.setObjetivo(
                    rutina.getObjetivo()
            );

            actual.setNivel(
                    rutina.getNivel()
            );

            actual.setDuracion(
                    rutina.getDuracion()
            );

            actual.setActivo(
                    rutina.isActivo()
            );

            rutinaService.save(
                    actual,
                    usuario
            );

        } else {

            rutinaService.save(
                    rutina,
                    usuario
            );
        }

        return "redirect:/rutina/listado";
    }

    @GetMapping("/editar/{idRutina}")
    public String editar(
            @PathVariable Integer idRutina,
            Principal principal,
            Model model) {

        Usuario usuario
                = getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        var rutina
                = rutinaService.getRutinaUsuario(
                        idRutina,
                        usuario.getIdUsuario()
                );

        if (rutina.isEmpty()) {
            return "redirect:/rutina/listado";
        }

        model.addAttribute(
                "rutina",
                rutina.get()
        );

        return "/rutina/modifica";
    }

    @GetMapping("/detalle/{idRutina}")
    public String detalle(
            @PathVariable Integer idRutina,
            Principal principal,
            Model model) {

        Usuario usuario
                = getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        var rutina
                = rutinaService.getRutinaUsuario(
                        idRutina,
                        usuario.getIdUsuario()
                );

        if (rutina.isEmpty()) {
            return "redirect:/rutina/listado";
        }

        model.addAttribute(
                "rutina",
                rutina.get()
        );

        model.addAttribute(
                "ejerciciosDisponibles",
                ejercicioService.getEjercicios(true)
        );

        return "/rutina/detalle";
    }

    @PostMapping("/agregarEjercicio")
    public String agregarEjercicio(
            @RequestParam Integer idRutina,
            @RequestParam Integer idEjercicio,
            Principal principal) {

        Usuario usuario
                = getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        var rutina
                = rutinaService.getRutinaUsuario(
                        idRutina,
                        usuario.getIdUsuario()
                );

        var ejercicio
                = ejercicioService.getEjercicio(
                        idEjercicio
                );

        if (rutina.isPresent()
                && ejercicio.isPresent()) {

            rutinaService.agregarEjercicio(
                    rutina.get(),
                    ejercicio.get()
            );
        }

        return "redirect:/rutina/detalle/"
                + idRutina;
    }

    @GetMapping(
            "/eliminarEjercicio/{idRutina}/{idEjercicio}"
    )
    public String eliminarEjercicio(
            @PathVariable Integer idRutina,
            @PathVariable Integer idEjercicio,
            Principal principal) {

        Usuario usuario
                = getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        var rutina
                = rutinaService.getRutinaUsuario(
                        idRutina,
                        usuario.getIdUsuario()
                );

        if (rutina.isPresent()) {

            rutinaService.eliminarEjercicio(
                    rutina.get(),
                    idEjercicio
            );
        }

        return "redirect:/rutina/detalle/"
                + idRutina;
    }

    @GetMapping("/eliminar/{idRutina}")
    public String eliminar(
            @PathVariable Integer idRutina,
            Principal principal) {

        Usuario usuario
                = getUsuario(principal);

        if (usuario == null) {
            return "redirect:/login";
        }

        var rutina
                = rutinaService.getRutinaUsuario(
                        idRutina,
                        usuario.getIdUsuario()
                );

        if (rutina.isPresent()) {

            rutinaService.delete(
                    idRutina
            );
        }

        return "redirect:/rutina/listado";
    }

    @GetMapping("/exportar/{idRutina}")
    public void exportar(
            @PathVariable Integer idRutina,
            Principal principal,
            HttpServletResponse response)
            throws IOException {

        Usuario usuario
                = getUsuario(principal);

        if (usuario == null) {

            response.sendRedirect(
                    "/login"
            );

            return;
        }

        var rutinaOptional
                = rutinaService.getRutinaUsuario(
                        idRutina,
                        usuario.getIdUsuario()
                );

        if (rutinaOptional.isEmpty()) {

            response.sendRedirect(
                    "/rutina/listado"
            );

            return;
        }

        Rutina rutina
                = rutinaOptional.get();

        response.setContentType(
                "application/pdf"
        );

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=\"rutina-"
                + rutina.getIdRutina()
                + ".pdf\""
        );

        try (PDDocument documento
                = new PDDocument()) {

            PDPage pagina
                    = new PDPage(
                            PDRectangle.A4
                    );

            documento.addPage(
                    pagina
            );

            PDType1Font fuenteNormal
                    = new PDType1Font(
                            Standard14Fonts.FontName.HELVETICA
                    );

            PDType1Font fuenteNegrita
                    = new PDType1Font(
                            Standard14Fonts.FontName.HELVETICA_BOLD
                    );

            float margen = 55;
            float y = 790;

            try (PDPageContentStream contenido
                    = new PDPageContentStream(
                            documento,
                            pagina
                    )) {

                /*
                 * Verde FIDNESS
                 * RGB 25, 135, 84
                 * PDFBox utiliza valores entre 0 y 1.
                 */
                contenido.setNonStrokingColor(
                        25f / 255f,
                        135f / 255f,
                        84f / 255f
                );

                escribirTexto(
                        contenido,
                        fuenteNegrita,
                        24,
                        margen,
                        y,
                        "FIDNESS"
                );

                y -= 35;

                /*
                 * Gris oscuro
                 * RGB 33, 37, 41
                 */
                contenido.setNonStrokingColor(
                        33f / 255f,
                        37f / 255f,
                        41f / 255f
                );

                escribirTexto(
                        contenido,
                        fuenteNegrita,
                        18,
                        margen,
                        y,
                        rutina.getNombre()
                );

                y -= 30;

                escribirTexto(
                        contenido,
                        fuenteNegrita,
                        11,
                        margen,
                        y,
                        "Usuario:"
                );

                escribirTexto(
                        contenido,
                        fuenteNormal,
                        11,
                        margen + 60,
                        y,
                        usuario.getNombre()
                        + " "
                        + usuario.getApellidos()
                );

                y -= 20;

                escribirTexto(
                        contenido,
                        fuenteNegrita,
                        11,
                        margen,
                        y,
                        "Objetivo:"
                );

                y = escribirParrafo(
                        contenido,
                        fuenteNormal,
                        11,
                        margen + 60,
                        y,
                        rutina.getObjetivo(),
                        430
                );

                y -= 8;

                escribirTexto(
                        contenido,
                        fuenteNegrita,
                        11,
                        margen,
                        y,
                        "Nivel:"
                );

                escribirTexto(
                        contenido,
                        fuenteNormal,
                        11,
                        margen + 60,
                        y,
                        rutina.getNivel()
                );

                y -= 20;

                escribirTexto(
                        contenido,
                        fuenteNegrita,
                        11,
                        margen,
                        y,
                        "Duracion:"
                );

                escribirTexto(
                        contenido,
                        fuenteNormal,
                        11,
                        margen + 60,
                        y,
                        rutina.getDuracion()
                        + " minutos"
                );

                y -= 35;

                /*
                 * Azul Bootstrap/FIDNESS
                 * RGB 13, 110, 253
                 */
                contenido.setNonStrokingColor(
                        13f / 255f,
                        110f / 255f,
                        253f / 255f
                );

                escribirTexto(
                        contenido,
                        fuenteNegrita,
                        16,
                        margen,
                        y,
                        "Ejercicios"
                );

                /*
                 * Volvemos al gris oscuro.
                 */
                contenido.setNonStrokingColor(
                        33f / 255f,
                        37f / 255f,
                        41f / 255f
                );

                y -= 25;

                if (rutina.getEjercicios().isEmpty()) {

                    escribirTexto(
                            contenido,
                            fuenteNormal,
                            11,
                            margen,
                            y,
                            "Esta rutina no tiene ejercicios registrados."
                    );

                    y -= 20;

                } else {

                    int numero = 1;

                    for (var ejercicio
                            : rutina.getEjercicios()) {

                        if (y < 120) {
                            break;
                        }

                        escribirTexto(
                                contenido,
                                fuenteNegrita,
                                12,
                                margen,
                                y,
                                numero
                                + ". "
                                + ejercicio.getNombre()
                        );

                        y -= 18;

                        escribirTexto(
                                contenido,
                                fuenteNormal,
                                10,
                                margen + 15,
                                y,
                                "Grupo muscular: "
                                + ejercicio.getGrupoMuscular()
                                + "   |   Nivel: "
                                + ejercicio.getNivel()
                        );

                        y -= 17;

                        escribirTexto(
                                contenido,
                                fuenteNormal,
                                10,
                                margen + 15,
                                y,
                                "Series: "
                                + ejercicio.getSeries()
                                + "   |   Repeticiones: "
                                + ejercicio.getRepeticiones()
                        );

                        y -= 17;

                        if (ejercicio.getDescripcion()
                                != null
                                && !ejercicio
                                        .getDescripcion()
                                        .isBlank()) {

                            y = escribirParrafo(
                                    contenido,
                                    fuenteNormal,
                                    10,
                                    margen + 15,
                                    y,
                                    ejercicio.getDescripcion(),
                                    480
                            );
                        }

                        y -= 15;

                        numero++;
                    }
                }

                y -= 10;

                if (y > 70) {

                    /*
                     * Gris para el pie.
                     * RGB 100, 100, 100
                     */
                    contenido.setNonStrokingColor(
                            100f / 255f,
                            100f / 255f,
                            100f / 255f
                    );

                    escribirTexto(
                            contenido,
                            fuenteNormal,
                            9,
                            margen,
                            y,
                            "FIDNESS - Entrena inteligente. Vive saludable."
                    );
                }
            }

            documento.save(
                    response.getOutputStream()
            );
        }
    }

    private void escribirTexto(
            PDPageContentStream contenido,
            PDType1Font fuente,
            float tamano,
            float x,
            float y,
            String texto)
            throws IOException {

        if (texto == null) {
            texto = "";
        }

        contenido.beginText();

        contenido.setFont(
                fuente,
                tamano
        );

        contenido.newLineAtOffset(
                x,
                y
        );

        contenido.showText(
                limpiarTexto(texto)
        );

        contenido.endText();
    }

    private float escribirParrafo(
            PDPageContentStream contenido,
            PDType1Font fuente,
            float tamano,
            float x,
            float y,
            String texto,
            float anchoMaximo)
            throws IOException {

        if (texto == null
                || texto.isBlank()) {

            return y;
        }

        String[] palabras
                = limpiarTexto(texto)
                        .split("\\s+");

        StringBuilder linea
                = new StringBuilder();

        for (String palabra
                : palabras) {

            String prueba;

            if (linea.length() == 0) {

                prueba
                        = palabra;

            } else {

                prueba
                        = linea
                        + " "
                        + palabra;
            }

            float ancho
                    = fuente
                            .getStringWidth(
                                    prueba
                            )
                    / 1000
                    * tamano;

            if (ancho > anchoMaximo
                    && linea.length() > 0) {

                escribirTexto(
                        contenido,
                        fuente,
                        tamano,
                        x,
                        y,
                        linea.toString()
                );

                y -= 15;

                linea
                        = new StringBuilder(
                                palabra
                        );

            } else {

                if (linea.length() > 0) {

                    linea.append(" ");
                }

                linea.append(
                        palabra
                );
            }
        }

        if (linea.length() > 0) {

            escribirTexto(
                    contenido,
                    fuente,
                    tamano,
                    x,
                    y,
                    linea.toString()
            );

            y -= 15;
        }

        return y;
    }

    private String limpiarTexto(
            String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace("ñ", "n")
                .replace("Ñ", "N");
    }
}
