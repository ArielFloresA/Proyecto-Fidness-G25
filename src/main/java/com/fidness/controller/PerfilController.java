package com.fidness.controller;

import com.fidness.domain.Usuario;
import com.fidness.service.UsuarioService;
import java.security.Principal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PerfilController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public PerfilController(
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder) {

        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/perfil")
    public String perfil(
            Principal principal,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario =
                usuarioService.getUsuarioPorCorreo(
                        principal.getName()
                );

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "usuario",
                usuario
        );

        return "/perfil";
    }

    @PostMapping("/perfil/guardar")
    public String guardar(
            Principal principal,
            String nombre,
            String apellidos,
            String correo,
            String telefono,
            MultipartFile imagenFile,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario =
                usuarioService.getUsuarioPorCorreo(
                        principal.getName()
                );

        if (usuario == null) {
            return "redirect:/login";
        }

        if (nombre == null
                || nombre.isBlank()
                || apellidos == null
                || apellidos.isBlank()
                || correo == null
                || correo.isBlank()) {

            model.addAttribute(
                    "error",
                    "Debe completar los campos obligatorios."
            );

            model.addAttribute(
                    "usuario",
                    usuario
            );

            return "/perfil";
        }

        Usuario otroUsuario =
                usuarioService.getUsuarioPorCorreo(
                        correo
                );

        if (otroUsuario != null
                && !otroUsuario.getIdUsuario()
                        .equals(usuario.getIdUsuario())) {

            model.addAttribute(
                    "error",
                    "Ese correo electrónico ya está registrado."
            );

            model.addAttribute(
                    "usuario",
                    usuario
            );

            return "/perfil";
        }

        usuarioService.actualizarPerfil(
                usuario,
                nombre,
                apellidos,
                correo,
                telefono,
                imagenFile
        );

        return "redirect:/perfil?actualizado";
    }

    @GetMapping("/perfil/password")
    public String cambiarPassword(
            Principal principal,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario =
                usuarioService.getUsuarioPorCorreo(
                        principal.getName()
                );

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "usuario",
                usuario
        );

        return "/password";
    }

    @PostMapping("/perfil/password/guardar")
    public String guardarPassword(
            Principal principal,
            String passwordActual,
            String nuevaPassword,
            String confirmarPassword,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario =
                usuarioService.getUsuarioPorCorreo(
                        principal.getName()
                );

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "usuario",
                usuario
        );

        if (passwordActual == null
                || passwordActual.isBlank()
                || nuevaPassword == null
                || nuevaPassword.isBlank()
                || confirmarPassword == null
                || confirmarPassword.isBlank()) {

            model.addAttribute(
                    "error",
                    "Debe completar todos los campos."
            );

            return "/password";
        }

        if (!passwordEncoder.matches(
                passwordActual,
                usuario.getPassword())) {

            model.addAttribute(
                    "error",
                    "La contraseña actual no es correcta."
            );

            return "/password";
        }

        if (nuevaPassword.length() < 4) {

            model.addAttribute(
                    "error",
                    "La nueva contraseña debe tener al menos 4 caracteres."
            );

            return "/password";
        }

        if (!nuevaPassword.equals(
                confirmarPassword)) {

            model.addAttribute(
                    "error",
                    "Las nuevas contraseñas no coinciden."
            );

            return "/password";
        }

        if (passwordEncoder.matches(
                nuevaPassword,
                usuario.getPassword())) {

            model.addAttribute(
                    "error",
                    "La nueva contraseña debe ser diferente a la actual."
            );

            return "/password";
        }

        usuarioService.actualizarPassword(
                usuario,
                nuevaPassword
        );

        return "redirect:/perfil?passwordActualizado";
    }
}
