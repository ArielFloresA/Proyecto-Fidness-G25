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
@Table(name = "ejercicio")
public class Ejercicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private Integer idEjercicio;

    @NotNull
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String nombre;

    @NotNull
    @Size(max = 300)
    @Column(nullable = false, length = 300)
    private String descripcion;

    @Column(length = 1000)
    private String instrucciones;

    @Column(length = 1000)
    private String recomendaciones;

    @NotNull
    @Size(max = 40)
    @Column(name = "grupo_muscular", nullable = false, length = 40)
    private String grupoMuscular;

    @NotNull
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String nivel;

    @NotNull
    @Size(max = 40)
    @Column(name = "tipo_entrenamiento", nullable = false, length = 40)
    private String tipoEntrenamiento;

    @NotNull
    @Column(nullable = false)
    private Integer series;

    @NotNull
    @Column(nullable = false)
    private Integer repeticiones;

    @Size(max = 1024)
    @Column(length = 1024)
    private String imagen;

    @Column(nullable = false)
    private boolean activo;
}
