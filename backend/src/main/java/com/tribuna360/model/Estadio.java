package com.tribuna360.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "estadio")
@Data  // Lombok generará automáticamente los métodos getters, setters, etc.
@NoArgsConstructor  // Lombok generará un constructor sin argumentos
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadio;

    private String nombre;  // Nombre del estadio
    private String ubicacion;  // Ubicación del estadio
    private int capacidad;  // Capacidad del estadio
}
