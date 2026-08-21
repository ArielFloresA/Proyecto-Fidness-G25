package com.fidness.repository;

import com.fidness.domain.Ejercicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioRepository
        extends JpaRepository<Ejercicio, Integer> {

    public List<Ejercicio> findByActivoTrue();

    public List<Ejercicio>
            findByActivoTrueAndGrupoMuscularIgnoreCase(
                    String grupoMuscular);

    public List<Ejercicio>
            findByActivoTrueAndNivelIgnoreCase(
                    String nivel);

    public List<Ejercicio>
            findByActivoTrueAndTipoEntrenamientoIgnoreCase(
                    String tipoEntrenamiento);

    public List<Ejercicio>
            findByActivoTrueAndGrupoMuscularIgnoreCaseAndNivelIgnoreCase(
                    String grupoMuscular,
                    String nivel);

    public List<Ejercicio>
            findByActivoTrueAndGrupoMuscularIgnoreCaseAndTipoEntrenamientoIgnoreCase(
                    String grupoMuscular,
                    String tipoEntrenamiento);

    public List<Ejercicio>
            findByActivoTrueAndNivelIgnoreCaseAndTipoEntrenamientoIgnoreCase(
                    String nivel,
                    String tipoEntrenamiento);

    public List<Ejercicio>
            findByActivoTrueAndGrupoMuscularIgnoreCaseAndNivelIgnoreCaseAndTipoEntrenamientoIgnoreCase(
                    String grupoMuscular,
                    String nivel,
                    String tipoEntrenamiento);
}
