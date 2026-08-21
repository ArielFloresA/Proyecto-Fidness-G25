package com.fidness.controller;

import com.fidness.domain.Usuario;
import com.fidness.service.RegistroService;
import com.fidness.service.UsuarioService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegistroService registroService;

    @GetMapping("/registro")
    public String registro(Model model) {

        model.addAttribute(
                "usuario",
                new Usuario()
        );

        return "/registro";
    }

    @PostMapping("/registro/guardar")
    public String guardar(
            Usuario usuario,
            String confirmarPassword,
            Model model) {

        if (usuario.getNombre() == null
                || usuario.getNombre().isBlank()
                || usuario.getApellidos() == null
                || usuario.getApellidos().isBlank()
                || usuario.getCorreo() == null
                || usuario.getCorreo().isBlank()
                || usuario.getPassword() == null
                || usuario.getPassword().isBlank()) {

            model.addAttribute(
                    "error",
                    "Debe completar todos los campos obligatorios."
            );

            model.addAttribute(
                    "usuario",
                    usuario
            );

            return "/registro";
        }

        if (!usuario.getCorreo().contains("@")) {

            model.addAttribute(
                    "error",
                    "Debe ingresar un correo electrónico válido."
            );

            model.addAttribute(
                    "usuario",
                    usuario
            );

            return "/registro";
        }

        if (!usuario.getPassword()
                .equals(confirmarPassword)) {

            model.addAttribute(
                    "error",
                    "Las contraseñas no coinciden."
            );

            model.addAttribute(
                    "usuario",
                    usuario
            );

            return "/registro";
        }

        if (usuarioService.existeCorreo(
                usuario.getCorreo())) {

            model.addAttribute(
                    "error",
                    "Ya existe una cuenta registrada con ese correo."
            );

            model.addAttribute(
                    "usuario",
                    usuario
            );

            return "/registro";
        }

        String username
                = usuario.getCorreo()
                        .substring(
                                0,
                                usuario.getCorreo()
                                        .indexOf("@")
                        );

        String usernameBase = username;

        int numero = 1;

        while (usuarioService.existeUsername(username)) {

            username
                    = usernameBase + numero;

            numero++;
        }

        usuario.setUsername(username);

        try {

            registroService.registrar(usuario);

        } catch (MessagingException e) {

            model.addAttribute(
                    "error",
                    "La cuenta fue registrada, pero no fue posible enviar el correo de activación."
            );

            model.addAttribute(
                    "usuario",
                    usuario
            );

            return "/registro";
        }

        return "redirect:/login?correoEnviado";
    }

    @GetMapping("/registro/activar/{username}/{token}")
    public String activarCuenta(
            @PathVariable String username,
            @PathVariable String token) {

        boolean activado
                = registroService.activarCuenta(
                        username,
                        token
                );

        if (activado) {

            return "redirect:/login?activado";
        }

        return "redirect:/login?activacionError";
    }
}