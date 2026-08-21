package com.fidness.config;

import com.fidness.service.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Configuracion {

    private final UsuarioDetailsService usuarioDetailsService;

    public Configuracion(
            UsuarioDetailsService usuarioDetailsService) {

        this.usuarioDetailsService =
                usuarioDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider(
                        usuarioDetailsService
                );

        authenticationProvider.setPasswordEncoder(
                passwordEncoder()
        );

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
                .authenticationProvider(
                        authenticationProvider()
                )

                .authorizeHttpRequests(
                        authorize -> authorize

                                .requestMatchers(
                                        "/",
                                        "/index",
                                        "/login",
                                        "/registro/**",
                                        "/recuperar/**",
                                        "/acerca",
                                        "/css/**",
                                        "/js/**",
                                        "/images/**",
                                        "/webjars/**",
                                        "/ejercicio/listado",
                                        "/ejercicio/detalle/**",
                                        "/membresia/**",
                                        "/clase/listado"
                                )
                                .permitAll()

                                .requestMatchers(
                                        "/admin/**",
                                        "/ejercicio/nuevo",
                                        "/ejercicio/editar/**",
                                        "/ejercicio/eliminar/**",
                                        "/ejercicio/guardar"
                                )
                                .hasRole("ADMIN")

                                .requestMatchers(
                                        "/perfil/**",
                                        "/rutina/**",
                                        "/progreso/**",
                                        "/reserva/**",
                                        "/clase/reservar/**",
                                        "/ejercicio/agregarRutina/**"
                                )
                                .authenticated()

                                .anyRequest()
                                .permitAll()
                )

                .formLogin(
                        form -> form

                                .loginPage(
                                        "/login"
                                )

                                .loginProcessingUrl(
                                        "/login"
                                )

                                .usernameParameter(
                                        "correo"
                                )

                                .passwordParameter(
                                        "password"
                                )

                                .defaultSuccessUrl(
                                        "/",
                                        true
                                )

                                .failureUrl(
                                        "/login?error"
                                )

                                .permitAll()
                )

                .rememberMe(
                        remember -> remember

                                .key(
                                        "fidnessClaveRememberMe2026"
                                )

                                .rememberMeParameter(
                                        "remember-me"
                                )

                                .tokenValiditySeconds(
                                        60 * 60 * 24 * 14
                                )

                                .userDetailsService(
                                        usuarioDetailsService
                                )
                )

                .logout(
                        logout -> logout

                                .logoutUrl(
                                        "/logout"
                                )

                                .logoutSuccessUrl(
                                        "/login?logout"
                                )

                                .invalidateHttpSession(
                                        true
                                )

                                .deleteCookies(
                                        "JSESSIONID",
                                        "remember-me"
                                )

                                .permitAll()
                );

        return http.build();
    }
}
