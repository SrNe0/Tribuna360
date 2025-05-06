package com.tribuna360.service;

import com.tribuna360.model.Club;
import com.tribuna360.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    public Club crearClub(Club club) {
        return clubRepository.save(club);  // Guardamos un club en la base de datos
    }

    public Club obtenerClubPorId(Long id) {
        return clubRepository.findById(id).orElse(null);  // Buscamos un club por su ID
    }

    public void eliminarClub(Long id) {
        clubRepository.deleteById(id);  // Eliminamos un club por su ID
    }
}
