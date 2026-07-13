package com.fidness.repository;

import com.fidness.domain.Rutina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina,Integer>{

    public List<Rutina> findByActivoTrue();

}
