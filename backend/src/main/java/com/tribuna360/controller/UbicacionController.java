package com.tribuna360.controller;

import com.tribuna360.model.Ubicacion;
import com.tribuna360.service.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    // Endpoint para crear una nueva ubicación
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ubicacion crearUbicacion(@RequestBody Ubicacion ubicacion) {
        return ubicacionService.crearUbicacion(ubicacion);
    }

    // Endpoint para obtener una ubicación por ID
    @GetMapping("/{id}")
    public Ubicacion obtenerUbicacionPorId(@PathVariable Long id) {
        return ubicacionService.obtenerUbicacionPorId(id);
    }

    // Endpoint para eliminar una ubicación por ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarUbicacion(@PathVariable Long id) {
        ubicacionService.eliminarUbicacion(id);
    }
}
