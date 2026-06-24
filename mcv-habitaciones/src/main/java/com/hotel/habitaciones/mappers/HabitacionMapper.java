package com.hotel.habitaciones.mappers;

import org.springframework.stereotype.Component;
import com.hotel.commons.mappers.CommonMapper;
import com.hotel.habitaciones.dto.HabitacionRequest;
import com.hotel.habitaciones.dto.HabitacionResponse;
import com.hotel.habitaciones.entities.Habitacion;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion> {

    @Override
    public Habitacion requestAEntidad(HabitacionRequest request) {
        if (request == null) return null;
        return Habitacion.crear(
                request.numero(),
                request.tipo(),
                request.precio(),
                request.capacidad()
        );
    }

    @Override
    public HabitacionResponse entidadAResponse(Habitacion habitacion) {
        if (habitacion == null) return null;
        return new HabitacionResponse(
                habitacion.getId(),
                habitacion.getNumero(),
                habitacion.getTipo(),
                habitacion.getPrecio(),
                habitacion.getCapacidad(),
                habitacion.getEstadoHabitacion().name(),
                habitacion.getEstadoHabitacion().getCodigo().intValue(),
                habitacion.getEstadoRegistro().name()
        );
    }
}