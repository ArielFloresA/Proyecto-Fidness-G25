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
public class RegistroService {

    private final UsuarioService usuarioService;
    private final CorreoService correoService;

    @Value("${fidness.servidor}")
    private String servidor;

    @Value("${fidness.clave.activacion}")
    private String claveActivacion;

    public RegistroService(
            UsuarioService usuarioService,
            CorreoService correoService) {

        this.usuarioService = usuarioService;
        this.correoService = correoService;
    }

    @Transactional
    public Usuario registrar(Usuario usuario)
            throws MessagingException {

        usuario.setActivo(false);

        Usuario usuarioGuardado
                = usuarioService.registrarUsuarioInactivo(usuario);

        String token = generarToken(usuarioGuardado);

        enviarCorreoActivacion(usuarioGuardado, token);

        return usuarioGuardado;
    }

    @Transactional
    public boolean activarCuenta(
            String username,
            String token) {

        Usuario usuario
                = usuarioService.getUsuarioPorUsername(username);

        if (usuario == null) {
            return false;
        }

        if (usuario.isActivo()) {
            return true;
        }

        String tokenEsperado = generarToken(usuario);

        if (!tokenEsperado.equals(token)) {
            return false;
        }

        usuario.setActivo(true);

        usuarioService.guardarUsuario(usuario);

        return true;
    }

    private String generarToken(Usuario usuario) {

        try {

            String contenido
                    = usuario.getUsername()
                    + usuario.getPassword()
                    + claveActivacion;

            MessageDigest digest
                    = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    contenido.getBytes(
                            StandardCharsets.UTF_8
                    )
            );

            StringBuilder resultado
                    = new StringBuilder();

            for (byte dato : hash) {
                resultado.append(
                        String.format("%02x", dato)
                );
            }

            return resultado.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(
                    "No fue posible generar el token de activación.",
                    e
            );
        }
    }

    private void enviarCorreoActivacion(
            Usuario usuario,
            String token)
            throws MessagingException {

        String enlace
                = servidor
                + "/registro/activar/"
                + usuario.getUsername()
                + "/"
                + token;

        String contenido
                = """
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
                          ¡Hola %s %s!
                      </h2>

                      <p>
                          Gracias por registrarte en FIDNESS.
                      </p>

                      <p>
                          Para completar la creación de tu cuenta,
                          debes confirmar tu correo electrónico.
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
                             Activar mi cuenta
                          </a>
                      </p>

                      <p>
                          Después de activar tu cuenta podrás
                          iniciar sesión y utilizar las funciones
                          de FIDNESS.
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
                "Activa tu cuenta de FIDNESS",
                contenido
        );
    }
}