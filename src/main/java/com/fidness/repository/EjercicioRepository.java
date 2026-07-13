package com.fidness.repository;

import com.fidness.domain.Ejercicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Integer> {

    public List<Ejercicio> findByActivoTrue();

}
