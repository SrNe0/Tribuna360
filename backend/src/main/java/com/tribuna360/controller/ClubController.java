package com.tribuna360.controller;

import com.tribuna360.model.Club;
import com.tribuna360.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;

    // Endpoint para crear un nuevo club
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Club crearClub(@RequestBody Club club) {
        return clubService.crearClub(club);
    }

    // Endpoint para obtener un club por ID
    @GetMapping("/{id}")
    public Club obtenerClubPorId(@PathVariable Long id) {
        return clubService.obtenerClubPorId(id);
    }

    // Endpoint para eliminar un club por ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarClub(@PathVariable Long id) {
        clubService.eliminarClub(id);
    }
}
