package com.fidness.controller;

import com.fidness.domain.Usuario;
import com.fidness.service.RolService;
import com.fidness.service.UsuarioService;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/usuario")
public class AdminUsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public AdminUsuarioController(
            UsuarioService usuarioService,
            RolService rolService) {

        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {

        model.addAttribute(
                "usuarios",
                usuarioService.getUsuarios()
        );

        return "/admin/usuario/listado";
    }

    @GetMapping("/detalle/{idUsuario}")
    public String detalle(
            @PathVariable Long idUsuario,
            Model model) {

        Usuario usuario =
                usuarioService.getUsuario(idUsuario);

        if (usuario == null) {
            return "redirect:/admin/usuario/listado";
        }

        model.addAttribute(
                "usuario",
                usuario
        );

        model.addAttribute(
                "roles",
                rolService.getRolesUsuario(idUsuario)
        );

        model.addAttribute(
                "tieneUser",
                rolService.tieneRol(
                        idUsuario,
                        "ROLE_USER"
                )
        );

        model.addAttribute(
                "tieneAdmin",
                rolService.tieneRol(
                        idUsuario,
                        "ROLE_ADMIN"
                )
        );

        return "/admin/usuario/detalle";
    }

    @GetMapping("/agregarRol/{idUsuario}/{rol}")
    public String agregarRol(
            @PathVariable Long idUsuario,
            @PathVariable String rol) {

        Usuario usuario =
                usuarioService.getUsuario(idUsuario);

        if (usuario == null) {
            return "redirect:/admin/usuario/listado";
        }

        String nombreRol =
                convertirRol(rol);

        if (nombreRol != null) {

            rolService.agregarRol(
                    usuario,
                    nombreRol
            );
        }

        return "redirect:/admin/usuario/detalle/"
                + idUsuario
                + "?rolAgregado";
    }

    @GetMapping("/eliminarRol/{idUsuario}/{rol}")
    public String eliminarRol(
            @PathVariable Long idUsuario,
            @PathVariable String rol,
            Principal principal) {

        Usuario usuario =
                usuarioService.getUsuario(idUsuario);

        if (usuario == null) {
            return "redirect:/admin/usuario/listado";
        }

        String nombreRol =
                convertirRol(rol);

        if (nombreRol == null) {

            return "redirect:/admin/usuario/detalle/"
                    + idUsuario;
        }

        if ("ROLE_ADMIN".equals(nombreRol)
                && esUsuarioActual(usuario, principal)) {

            return "redirect:/admin/usuario/detalle/"
                    + idUsuario
                    + "?propioAdmin";
        }

        rolService.eliminarRol(
                idUsuario,
                nombreRol
        );

        return "redirect:/admin/usuario/detalle/"
                + idUsuario
                + "?rolEliminado";
    }

    @GetMapping("/estado/{idUsuario}")
    public String cambiarEstado(
            @PathVariable Long idUsuario,
            Principal principal) {

        Usuario usuario =
                usuarioService.getUsuario(idUsuario);

        if (usuario == null) {
            return "redirect:/admin/usuario/listado";
        }

        if (esUsuarioActual(usuario, principal)) {

            return "redirect:/admin/usuario/detalle/"
                    + idUsuario
                    + "?propioEstado";
        }

        usuarioService.cambiarEstado(idUsuario);

        return "redirect:/admin/usuario/detalle/"
                + idUsuario
                + "?estado";
    }

    @GetMapping("/eliminar/{idUsuario}")
    public String eliminarUsuario(
            @PathVariable Long idUsuario,
            Principal principal) {

        Usuario usuario =
                usuarioService.getUsuario(idUsuario);

        if (usuario == null) {
            return "redirect:/admin/usuario/listado";
        }

        if (esUsuarioActual(usuario, principal)) {

            return "redirect:/admin/usuario/detalle/"
                    + idUsuario
                    + "?propioEliminar";
        }

        boolean eliminado =
                usuarioService.eliminarUsuario(
                        idUsuario
                );

        if (!eliminado) {

            return "redirect:/admin/usuario/detalle/"
                    + idUsuario
                    + "?tieneDatos";
        }

        return "redirect:/admin/usuario/listado"
                + "?eliminado";
    }

    private boolean esUsuarioActual(
            Usuario usuario,
            Principal principal) {

        if (usuario == null
                || principal == null) {

            return false;
        }

        String identificador =
                principal.getName();

        boolean mismoCorreo =
                usuario.getCorreo() != null
                && usuario.getCorreo()
                        .equalsIgnoreCase(
                                identificador
                        );

        boolean mismoUsername =
                usuario.getUsername() != null
                && usuario.getUsername()
                        .equalsIgnoreCase(
                                identificador
                        );

        return mismoCorreo || mismoUsername;
    }

    private String convertirRol(
            String rol) {

        if ("USER".equalsIgnoreCase(rol)) {
            return "ROLE_USER";
        }

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return "ROLE_ADMIN";
        }

        return null;
    }
}
