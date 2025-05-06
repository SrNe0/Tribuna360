package com.tribuna360.repository;

import com.tribuna360.model.Abono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbonoRepository extends JpaRepository<Abono, Long> {
    List<Abono> findByClub_IdClub(Long clubId);
}
