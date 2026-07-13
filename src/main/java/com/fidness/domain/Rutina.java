package com.fidness.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="rutina")
public class Rutina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rutina")
    private Integer idRutina;

    @NotNull
    @Size(max=60)
    private String nombre;

    @NotNull
    @Size(max=100)
    private String objetivo;

    @NotNull
    @Size(max=20)
    private String nivel;

    private int duracion;

    private boolean activo;

}
