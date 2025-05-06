package com.tribuna360.enums;

public enum AbonoEstadoEnum {
    ACTIVO,
    INACTIVO,
    VENCIDO;

    // Método para validar si el estado es válido
    public static boolean isValidEstado(String estado) {
        for (AbonoEstadoEnum estadoEnum : values()) {
            if (estadoEnum.name().equals(estado)) {
                return true;
            }
        }
        return false;
    }
}
