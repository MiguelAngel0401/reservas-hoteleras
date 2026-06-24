package com.hotel.habitaciones.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EstadoRegistro {

    ACTIVO("ACTIVO"),
    ELIMINADO("ELIMINADO");

    private final String valor;
}