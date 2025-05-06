package com.tribuna360.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "abono_usuario")
@Data
@NoArgsConstructor
public class AbonoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abono_usuario")
    private Long idAbonoUsuario;  // Identificador único para la relación

    @ManyToOne
    @JoinColumn(name = "id_abono", nullable = false)
    private Abono abono;  // Relación con el abono adquirido

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;  // Relación con el usuario que compra el abono

    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;  // Fecha en la que se realiza la compra del abono

    @Column(name = "estado", nullable = false)
    private String estado;  // Estado del abono (activo/inactivo)

    @Column(name = "id_pago")
    private String idPago;  // Información del pago (ID de transacción)

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;  // Método utilizado para pagar el abono (por ejemplo: tarjeta, efectivo, etc.)

    @Column(name = "monto_pagado", nullable = false)
    private BigDecimal montoPagado;  // Monto total pagado por el usuario

    @Column(name = "estado_pago", nullable = false)
    private String estadoPago;  // Estado del pago (pendiente, confirmado, etc.)

    @ManyToOne
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private Ubicacion ubicacion;  // Relación con la ubicación del abono
}
