package com.tribuna360.service;

import com.tribuna360.model.Estadio;
import com.tribuna360.repository.EstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadioService {

    @Autowired
    private EstadioRepository estadioRepository;

    public Estadio crearEstadio(Estadio estadio) {
        return estadioRepository.save(estadio);  // Guardamos un estadio en la base de datos
    }

    public Estadio obtenerEstadioPorId(Long id) {
        return estadioRepository.findById(id).orElse(null);  // Buscamos un estadio por su ID
    }

    public void eliminarEstadio(Long id) {
        estadioRepository.deleteById(id);  // Eliminamos un estadio por su ID
    }
}
