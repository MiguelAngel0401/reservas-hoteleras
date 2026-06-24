package com.hotel.habitaciones.services;

import com.hotel.commons.services.CrudService;
import com.hotel.habitaciones.dto.HabitacionRequest;
import com.hotel.habitaciones.dto.HabitacionResponse;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {
    HabitacionResponse cambiarEstado(Long id, Integer idEstado);
    HabitacionResponse obtenerHabitacionPorId(Long id);
}