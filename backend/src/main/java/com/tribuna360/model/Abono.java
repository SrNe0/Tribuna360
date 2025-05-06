package com.tribuna360.model;

import com.tribuna360.enums.AbonoEstadoEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "abono")
@Data  // Lombok generará automáticamente los métodos getters, setters, etc.
@NoArgsConstructor  // Lombok generará un constructor sin argumentos
public class Abono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAbono;

    @ManyToOne
    @JoinColumn(name = "id_club", nullable = false)
    private Club club;  // Relación con Club

    @ManyToOne
    @JoinColumn(name = "id_estadio", nullable = false)
    private Estadio estadio;  // Relación con Estadio

    @ManyToOne
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private Ubicacion ubicacion;  // Relación con Ubicacion

    private BigDecimal precio;  // Precio del abono

    @Enumerated(EnumType.STRING)
    private AbonoEstadoEnum estado;  // Estado del abono (ACTIVO, INACTIVO, VENCIDO)

    private LocalDate fechaInicio;  // Fecha de inicio del abono
    private LocalDate fechaFin;  // Fecha de fin del abono

}
