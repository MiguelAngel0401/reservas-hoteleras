package com.hotel.habitaciones.dto;

import java.math.BigDecimal;

public record HabitacionResponse(
        Long id,
        Integer numero,
        String tipo,
        BigDecimal precio,
        Integer capacidad,
        String estadoHabitacion,
        Integer idEstadoHabitacion,
        String estadoRegistro
) {}