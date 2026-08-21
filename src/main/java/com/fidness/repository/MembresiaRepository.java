package com.fidness.repository;

import com.fidness.domain.Membresia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembresiaRepository
        extends JpaRepository<Membresia, Integer> {

    List<Membresia> findByActivoTrue();
}
