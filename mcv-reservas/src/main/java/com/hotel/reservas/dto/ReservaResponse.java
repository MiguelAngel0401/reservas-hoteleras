package com.hotel.reservas.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.hotel.commons.dto.HuespedResponse;
import com.hotel.commons.dto.HabitacionResponse;
import lombok.Data;

@Data
public class ReservaResponse {
    private Long id;
    private HuespedResponse huesped;
    private HabitacionResponse habitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String estadoReserva;
    private Integer idEstadoReserva;
    private String estadoRegistro;
    private LocalDateTime fechaCreacion;
}