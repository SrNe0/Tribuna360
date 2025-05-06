package com.tribuna360.service;

import com.tribuna360.model.Ubicacion;
import com.tribuna360.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbicacionService {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    public Ubicacion crearUbicacion(Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);  // Guardamos una ubicación en la base de datos
    }

    public Ubicacion obtenerUbicacionPorId(Long id) {
        return ubicacionRepository.findById(id).orElse(null);  // Buscamos una ubicación por su ID
    }

    public void eliminarUbicacion(Long id) {
        ubicacionRepository.deleteById(id);  // Eliminamos una ubicación por su ID
    }
}

