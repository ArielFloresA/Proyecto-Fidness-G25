package com.fidness.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String username;

    private String password;

    private String nombre;

    private String apellidos;

    private String correo;

    private String telefono;

    @Column(columnDefinition = "TEXT")
    private String rutaImagen;

    private boolean activo;

    @OneToMany(
            mappedBy = "usuario",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Rol> roles;

    public Usuario() {
    }

    public Usuario(Long idUsuario, String username, String password,
            String nombre, String apellidos, String correo,
            String telefono, String rutaImagen, boolean activo) {

        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.rutaImagen = rutaImagen;
        this.activo = activo;
    }
}
