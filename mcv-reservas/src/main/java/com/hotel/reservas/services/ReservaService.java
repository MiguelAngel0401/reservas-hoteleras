package com.hotel.reservas.services;

import com.hotel.commons.services.CrudService;
import com.hotel.commons.dto.HuespedResponse;
import com.hotel.commons.dto.HabitacionResponse;
import com.hotel.reservas.dto.ReservaRequest;
import com.hotel.reservas.dto.ReservaResponse;

public interface ReservaService extends CrudService<ReservaRequest, ReservaResponse> {
    ReservaResponse cambiarEstado(Long idReserva, Integer idEstado);
}