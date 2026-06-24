package com.hotel.habitaciones.enums;

import java.util.EnumSet;
import java.util.Set;
import com.hotel.commons.exceptions.RecursoNoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EstadoHabitacion {

    DISPONIBLE(1L, "Disponible para asignarse", true) {
        @Override
        public Set<EstadoHabitacion> puedeCambiar() {
            return EnumSet.of(OCUPADA, LIMPIEZA, MANTENIMIENTO);
        }
    },

    OCUPADA(2L, "Asignada a una reserva", false) {
        @Override
        public Set<EstadoHabitacion> puedeCambiar() {
            return EnumSet.of(DISPONIBLE, LIMPIEZA);
        }
    },

    LIMPIEZA(3L, "En limpieza", true) {
        @Override
        public Set<EstadoHabitacion> puedeCambiar() {
            return EnumSet.of(DISPONIBLE, MANTENIMIENTO);
        }
    },

    MANTENIMIENTO(4L, "En reparación", true) {
        @Override
        public Set<EstadoHabitacion> puedeCambiar() {
            return EnumSet.of(DISPONIBLE, LIMPIEZA);
        }
    };

    private final Long codigo;
    private final String descripcion;
    private final boolean cambioManualPermitido;

    public abstract Set<EstadoHabitacion> puedeCambiar();

    public boolean puedeCambiarA(EstadoHabitacion nuevoEstado) {
        return this.puedeCambiar().contains(nuevoEstado);
    }

    public static EstadoHabitacion obtenerPorCodigo(Long codigo) {
        for (EstadoHabitacion e : values()) {
            if (e.codigo.equals(codigo)) return e;
        }
        throw new RecursoNoEncontradoException("Estado de habitación no válido: " + codigo);
    }
}