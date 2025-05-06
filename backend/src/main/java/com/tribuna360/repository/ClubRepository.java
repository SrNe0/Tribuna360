package com.tribuna360.repository;

import com.tribuna360.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    // Métodos personalizados si es necesario
}
