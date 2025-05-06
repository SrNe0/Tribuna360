package com.tribuna360.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "club")
@Data  // Lombok generará automáticamente los métodos getters, setters, etc.
@NoArgsConstructor  // Lombok generará un constructor sin argumentos
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClub;

    private String nombre;  // Nombre del club

    // Relación con Estadio (un club pertenece a un solo estadio)
    @ManyToOne
    @JoinColumn(name = "id_estadio", nullable = false)  // Relación con Estadio, un Club solo tiene un Estadio
    private Estadio estadio;
}
