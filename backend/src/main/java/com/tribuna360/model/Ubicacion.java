package com.tribuna360.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ubicacion")
@Data  // Lombok generará automáticamente los métodos getters, setters, etc.
@NoArgsConstructor  // Lombok generará un constructor sin argumentos
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUbicacion;

    private String zona;  // Zona del estadio (Norte, Sur, Este, Oeste)

}
