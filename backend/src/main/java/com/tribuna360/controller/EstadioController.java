package com.tribuna360.controller;

import com.tribuna360.model.Estadio;
import com.tribuna360.service.EstadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadios")
public class EstadioController {

    @Autowired
    private EstadioService estadioService;

    // Endpoint para crear un nuevo estadio
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estadio crearEstadio(@RequestBody Estadio estadio) {
        return estadioService.crearEstadio(estadio);
    }

    // Endpoint para obtener un estadio por ID
    @GetMapping("/{id}")
    public Estadio obtenerEstadioPorId(@PathVariable Long id) {
        return estadioService.obtenerEstadioPorId(id);
    }

    // Endpoint para eliminar un estadio por ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEstadio(@PathVariable Long id) {
        estadioService.eliminarEstadio(id);
    }
}
