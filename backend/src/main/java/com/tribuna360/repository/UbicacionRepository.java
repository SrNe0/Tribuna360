package com.tribuna360.repository;

import com.tribuna360.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    // Métodos personalizados si es necesario
}
