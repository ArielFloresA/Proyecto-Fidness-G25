package com.fidness.service;

import com.fidness.domain.Usuario;
import jakarta.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecuperacionService {

    private final UsuarioService usuarioService;
    private final CorreoService correoService;

    @Value("${fidness.servidor}")
    private String servidor;

    @Value("${fidness.clave.activacion}")
    private String claveActivacion;

    public RecuperacionService(
            UsuarioService usuarioService,
            CorreoService correoService) {

        this.usuarioService = usuarioService;
        this.correoService = correoService;
    }

    public boolean solicitarRecuperacion(String correo)
            throws MessagingException {

        Usuario usuario =
                usuarioService.getUsuarioPorCorreo(correo);

        if (usuario == null || !usuario.isActivo()) {
            return false;
        }

        String token = generarToken(usuario);

        enviarCorreoRecuperacion(usuario, token);

        return true;
    }

    public Usuario validarToken(
            String username,
            String token) {

        Usuario usuario =
                usuarioService.getUsuarioPorUsername(username);

        if (usuario == null || !usuario.isActivo()) {
            return null;
        }

        String tokenEsperado = generarToken(usuario);

        if (!tokenEsperado.equals(token)) {
            return null;
        }

        return usuario;
    }

    @Transactional
    public boolean cambiarPassword(
            String username,
            String token,
            String nuevaPassword) {

        Usuario usuario =
                validarToken(username, token);

        if (usuario == null) {
            return false;
        }

        usuarioService.actualizarPassword(
                usuario,
                nuevaPassword
        );

        return true;
    }

    private String generarToken(Usuario usuario) {

        try {

            String contenido =
                    usuario.getUsername()
                    + usuario.getPassword()
                    + claveActivacion
                    + "-RECUPERACION";

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            contenido.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            StringBuilder resultado =
                    new StringBuilder();

            for (byte dato : hash) {

                resultado.append(
                        String.format("%02x", dato)
                );
            }

            return resultado.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(
                    "No fue posible generar el token de recuperación.",
                    e
            );
        }
    }

    private void enviarCorreoRecuperacion(
            Usuario usuario,
            String token)
            throws MessagingException {

        String enlace =
                servidor
                + "/recuperar/restablecer/"
                + usuario.getUsername()
                + "/"
                + token;

        String contenido =
                """
                <div style="
                    font-family:Arial,sans-serif;
                    max-width:600px;
                    margin:auto;
                    padding:25px;
                    border:1px solid #ddd;
                    border-radius:12px;
                ">

                    <h1 style="color:#198754;">
                        FIDNESS
                    </h1>

                    <h2>
                        Hola %s %s
                    </h2>

                    <p>
                        Recibimos una solicitud para cambiar
                        la contraseña de tu cuenta.
                    </p>

                    <p>
                        Presiona el siguiente botón para
                        establecer una nueva contraseña.
                    </p>

                    <p style="margin:30px 0;">

                        <a href="%s"
                           style="
                               background:#198754;
                               color:white;
                               padding:12px 22px;
                               text-decoration:none;
                               border-radius:8px;
                               font-weight:bold;
                           ">

                            Cambiar mi contraseña

                        </a>

                    </p>

                    <p>
                        Si no solicitaste este cambio,
                        puedes ignorar este correo.
                    </p>

                    <hr>

                    <small style="color:#666;">
                        FIDNESS - Desarrollo de Aplicaciones Web
                        y Patrones
                    </small>

                </div>
                """.formatted(
                        usuario.getNombre(),
                        usuario.getApellidos(),
                        enlace
                );

        correoService.enviarCorreoHtml(
                usuario.getCorreo(),
                "Recuperación de contraseña - FIDNESS",
                contenido
        );
    }
}
