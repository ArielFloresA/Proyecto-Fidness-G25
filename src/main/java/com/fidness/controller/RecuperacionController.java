package com.fidness.controller;

import com.fidness.domain.Usuario;
import com.fidness.service.RecuperacionService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RecuperacionController {

    private final RecuperacionService recuperacionService;

    public RecuperacionController(
            RecuperacionService recuperacionService) {

        this.recuperacionService =
                recuperacionService;
    }

    @GetMapping("/recuperar")
    public String recuperar() {

        return "/recuperar";
    }

    @PostMapping("/recuperar/enviar")
    public String enviar(
            String correo,
            Model model) {

        if (correo == null || correo.isBlank()) {

            model.addAttribute(
                    "error",
                    "Debe ingresar su correo electrónico."
            );

            return "/recuperar";
        }

        try {

            boolean enviado =
                    recuperacionService
                            .solicitarRecuperacion(correo);

            if (!enviado) {

                model.addAttribute(
                        "error",
                        "No existe una cuenta activa con ese correo."
                );

                return "/recuperar";
            }

        } catch (MessagingException e) {

            model.addAttribute(
                    "error",
                    "No fue posible enviar el correo de recuperación."
            );

            return "/recuperar";
        }

        model.addAttribute(
                "mensaje",
                "Se envió un enlace de recuperación a tu correo electrónico."
        );

        return "/recuperar";
    }

    @GetMapping(
            "/recuperar/restablecer/{username}/{token}"
    )
    public String restablecer(
            @PathVariable String username,
            @PathVariable String token,
            Model model) {

        Usuario usuario =
                recuperacionService.validarToken(
                        username,
                        token
                );

        if (usuario == null) {

            return "redirect:/login?recuperacionError";
        }

        model.addAttribute(
                "username",
                username
        );

        model.addAttribute(
                "token",
                token
        );

        return "/restablecer";
    }

    @PostMapping("/recuperar/cambiar")
    public String cambiar(
            String username,
            String token,
            String password,
            String confirmarPassword,
            Model model) {

        if (password == null
                || password.isBlank()
                || password.length() < 4) {

            model.addAttribute(
                    "error",
                    "La contraseña debe tener al menos 4 caracteres."
            );

            model.addAttribute(
                    "username",
                    username
            );

            model.addAttribute(
                    "token",
                    token
            );

            return "/restablecer";
        }

        if (!password.equals(confirmarPassword)) {

            model.addAttribute(
                    "error",
                    "Las contraseñas no coinciden."
            );

            model.addAttribute(
                    "username",
                    username
            );

            model.addAttribute(
                    "token",
                    token
            );

            return "/restablecer";
        }

        boolean actualizado =
                recuperacionService.cambiarPassword(
                        username,
                        token,
                        password
                );

        if (!actualizado) {

            return "redirect:/login?recuperacionError";
        }

        return "redirect:/login?passwordActualizado";
    }
}
