package com.tribuna360.service;

import com.tribuna360.model.Abono;
import com.tribuna360.model.AbonoUsuario;
import com.tribuna360.repository.AbonoRepository;
import com.tribuna360.repository.AbonoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbonoService {

    @Autowired
    private AbonoRepository abonoRepository;

    @Autowired
    private AbonoUsuarioRepository abonoUsuarioRepository;

    public Abono crearAbono(Abono abono) {
        return abonoRepository.save(abono);  // Guardamos un abono en la base de datos
    }


    public Abono obtenerAbonoPorId(Long id) {
        return abonoRepository.findById(id).orElse(null);  // Buscamos un abono por su ID
    }

    public void eliminarAbono(Long id) {
        abonoRepository.deleteById(id);  // Eliminamos un abono por su ID
    }

    public List<Abono> obtenerAbonosPorClub(Long clubId) {

        return abonoRepository.findByClub_IdClub(clubId);
    }


    public List<Abono> obtenerAbonosPorUsuario(Long usuarioId) {

        List<AbonoUsuario> abonosUsuarios = abonoUsuarioRepository.findByUsuario_IdUsuario(usuarioId);

        return abonosUsuarios.stream()
                .map(AbonoUsuario::getAbono) // Obtener el objeto Abono desde AbonoUsuario
                .collect(Collectors.toList());
    }
}