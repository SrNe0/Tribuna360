package com.tribuna360.service;

import com.tribuna360.model.AbonoUsuario;
import com.tribuna360.repository.AbonoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbonoUsuarioService {

    @Autowired
    private AbonoUsuarioRepository abonoUsuarioRepository;

    public List<AbonoUsuario> obtenerAbonosPorUsuario(Long usuarioId) {
        return abonoUsuarioRepository.findByUsuario_IdUsuario(usuarioId);
    }

    // ... otros métodos
}
