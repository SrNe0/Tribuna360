package com.tribuna360.repository;

import com.tribuna360.model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadioRepository extends JpaRepository<Estadio, Long> {
    // Métodos personalizados si es necesario
}
