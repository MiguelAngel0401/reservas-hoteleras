package com.hotel.reservas.mappers;

import org.springframework.stereotype.Component;
import com.hotel.commons.dto.HabitacionResponse;
import com.hotel.commons.dto.HuespedResponse;
import com.hotel.commons.mappers.CommonMapper;
import com.hotel.reservas.dto.ReservaRequest;
import com.hotel.reservas.dto.ReservaResponse;
import com.hotel.reservas.entities.Reserva;

@Component
public class ReservaMapper implements CommonMapper<ReservaRequest, ReservaResponse, Reserva> {

    @Override
    public Reserva requestAEntidad(ReservaRequest request) {
        return Reserva.crear(
                request.idHuesped(),
                request.idHabitacion(),
                request.fechaEntrada(),
                request.fechaSalida()
        );
    }

    @Override
    public ReservaResponse entidadAResponse(Reserva reserva) {
        ReservaResponse response = new ReservaResponse();
        response.setId(reserva.getId());
        response.setFechaEntrada(reserva.getFechaEntrada());
        response.setFechaEntrada(reserva.getFechaEntrada());
        response.setFechaCreacion(reserva.getFechaCreacion());
        response.setEstadoReserva(reserva.getEstadoReserva().name());
        response.setIdEstadoReserva(reserva.getEstadoReserva().ordinal() + 1);
        response.setEstadoRegistro(reserva.getEstadoRegistro().name());
        return response;
    }

    public ReservaResponse entidadAResponseConDatos(Reserva reserva,
                                                     HuespedResponse huesped,
                                                     HabitacionResponse habitacion) {
        ReservaResponse response = entidadAResponse(reserva);
        response.setHuesped(huesped);
        response.setHabitacion(habitacion);
        response.setFechaSalida(reserva.getFechaSalida());
        return response;
    }
}