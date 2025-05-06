package com.tribuna360.repository;


import com.tribuna360.model.AbonoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbonoUsuarioRepository extends JpaRepository<AbonoUsuario, Long> {
    List<AbonoUsuario> findByUsuario_IdUsuario(Long usuarioId);
}

