package com.fidness.repository;

import com.fidness.domain.ClaseGrupal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaseGrupalRepository
        extends JpaRepository<ClaseGrupal, Integer> {

    List<ClaseGrupal> findByActivoTrueOrderByFechaAscHoraAsc();
}
