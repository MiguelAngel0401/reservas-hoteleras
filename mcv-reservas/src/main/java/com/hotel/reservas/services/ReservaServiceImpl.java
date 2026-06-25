package com.hotel.reservas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.commons.clients.HabitacionClient;
import com.hotel.commons.clients.HuespedClient;
import com.hotel.commons.dto.HabitacionResponse;
import com.hotel.commons.dto.HuespedResponse;
import com.hotel.commons.enums.EstadoHabitacion;
import com.hotel.commons.enums.EstadoRegistro;
import com.hotel.commons.exceptions.RecursoNoEncontradoException;
import com.hotel.reservas.dto.ReservaRequest;
import com.hotel.reservas.dto.ReservaResponse;
import com.hotel.reservas.entities.Reserva;
import com.hotel.reservas.mappers.ReservaMapper;
import com.hotel.reservas.repositories.ReservaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final HuespedClient huespedClient;
    private final HabitacionClient habitacionClient;

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponse> listar() {
        log.info("Listando reservas activas");
        return reservaRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream()
                .map(reserva -> {
                    HuespedResponse huesped = huespedClient
                            .obtenerHuespedSinEstadoPorId(reserva.getIdHuesped());
                    HabitacionResponse habitacion = habitacionClient
                            .obtenerHabitacionSinEstadoPorId(reserva.getIdHabitacion());
                    return reservaMapper.entidadAResponseConDatos(reserva, huesped, habitacion);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponse obtenerPorId(Long id) {
        log.info("Obteniendo reserva id {}", id);
        Reserva reserva = obtenerReservaActivaPorId(id);
        HuespedResponse huesped = huespedClient
                .obtenerHuespedSinEstadoPorId(reserva.getIdHuesped());
        HabitacionResponse habitacion = habitacionClient
                .obtenerHabitacionSinEstadoPorId(reserva.getIdHabitacion());
        return reservaMapper.entidadAResponseConDatos(reserva, huesped, habitacion);
    }

    @Override
    public ReservaResponse registrar(ReservaRequest request) {
        log.info("Registrando reserva para huésped {} habitación {}",
                request.idHuesped(), request.idHabitacion());

        HuespedResponse huesped = huespedClient
                .obtenerHuespedActivoPorId(request.idHuesped());
        if (huesped == null)
            throw new RecursoNoEncontradoException(
                    "No se encontró el huésped con id: " + request.idHuesped());

        HabitacionResponse habitacion = habitacionClient
                .obtenerHabitacionActivaPorId(request.idHabitacion());
        if (habitacion == null)
            throw new RecursoNoEncontradoException(
                    "No se encontró la habitación con id: " + request.idHabitacion());

        if (!habitacion.estadoHabitacion().equals(EstadoHabitacion.DISPONIBLE.name()))
            throw new IllegalStateException(
                    "La habitación no está disponible");

        Reserva reserva = reservaMapper.requestAEntidad(request);
        reserva = reservaRepository.save(reserva);

        habitacionClient.cambiarEstado(request.idHabitacion(), 2);

        habitacion = habitacionClient
                .obtenerHabitacionSinEstadoPorId(request.idHabitacion());

        return reservaMapper.entidadAResponseConDatos(reserva, huesped, habitacion);
    }

    @Override
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        log.info("Actualizando reserva id {}", id);
        Reserva reserva = obtenerReservaActivaPorId(id);

        reserva.actualizarFechas(request.fechaEntrada(), request.fechaSalida());
        reserva = reservaRepository.save(reserva);

        HuespedResponse huesped = huespedClient
                .obtenerHuespedSinEstadoPorId(reserva.getIdHuesped());
        HabitacionResponse habitacion = habitacionClient
                .obtenerHabitacionSinEstadoPorId(reserva.getIdHabitacion());

        return reservaMapper.entidadAResponseConDatos(reserva, huesped, habitacion);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando reserva id {}", id);
        Reserva reserva = obtenerReservaActivaPorId(id);
        reserva.eliminar();
        reservaRepository.save(reserva);
    }

    @Override
    public ReservaResponse cambiarEstado(Long idReserva, Integer idEstado) {
        log.info("Cambiando estado reserva {} a estado {}", idReserva, idEstado);
        Reserva reserva = obtenerReservaActivaPorId(idReserva);

        switch (idEstado) {
            case 2 -> {
                reserva.checkin();
                habitacionClient.actualizarEstadoInterno(reserva.getIdHabitacion(), 2);
            }
            case 3 -> {
                reserva.checkout();
                habitacionClient.actualizarEstadoInterno(reserva.getIdHabitacion(), 1);
            }
            case 4 -> {
                reserva.cancelar();
                habitacionClient.actualizarEstadoInterno(reserva.getIdHabitacion(), 1);
            }
            default -> throw new IllegalArgumentException(
                    "Estado no válido: " + idEstado);
        }

        reserva = reservaRepository.save(reserva);

        HuespedResponse huesped = huespedClient
                .obtenerHuespedSinEstadoPorId(reserva.getIdHuesped());
        HabitacionResponse habitacion = habitacionClient
                .obtenerHabitacionSinEstadoPorId(reserva.getIdHabitacion());

        return reservaMapper.entidadAResponseConDatos(reserva, huesped, habitacion);
    }

    private Reserva obtenerReservaActivaPorId(Long id) {
        return reservaRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la reserva con id: " + id));
    }
}