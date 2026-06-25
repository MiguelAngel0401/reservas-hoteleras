package com.hotel.reservas.entities;

import com.hotel.commons.enums.EstadoRegistro;
import com.hotel.commons.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVAS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA")
    private Long id;

    @Column(name = "ID_HUESPED", nullable = false)
    private Long idHuesped;

    @Column(name = "ID_HABITACION", nullable = false)
    private Long idHabitacion;

    @Column(name = "FECHA_ENTRADA", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "FECHA_SALIDA", nullable = false)
    private LocalDate fechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_RESERVA", nullable = false, length = 20)
    private EstadoReserva estadoReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false, length = 20)
    private EstadoRegistro estadoRegistro;

    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    public static Reserva crear(Long idHuesped, Long idHabitacion,
                                LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (fechaEntrada == null || fechaSalida == null)
            throw new IllegalArgumentException("Las fechas son obligatorias");
        if (!fechaEntrada.isBefore(fechaSalida))
            throw new IllegalArgumentException(
                "La fecha de entrada debe ser anterior a la de salida");
        return Reserva.builder()
                .idHuesped(idHuesped)
                .idHabitacion(idHabitacion)
                .fechaEntrada(fechaEntrada)
                .fechaSalida(fechaSalida)
                .estadoReserva(EstadoReserva.CONFIRMADA)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .fechaCreacion(LocalDateTime.now())
                .build();
    }

    public void checkin() {
        if (this.estadoReserva != EstadoReserva.CONFIRMADA)
            throw new IllegalStateException(
                "Solo se puede hacer check-in en reservas CONFIRMADAS");
        this.estadoReserva = EstadoReserva.EN_CURSO;
    }

    public void checkout() {
        if (this.estadoReserva != EstadoReserva.EN_CURSO)
            throw new IllegalStateException(
                "Solo se puede hacer check-out en reservas EN_CURSO");
        this.estadoReserva = EstadoReserva.FINALIZADA;
    }

    public void cancelar() {
        if (this.estadoReserva != EstadoReserva.CONFIRMADA)
            throw new IllegalStateException(
                "Solo se puede cancelar una reserva CONFIRMADA");
        this.estadoReserva = EstadoReserva.CANCELADA;
    }

    public void actualizarFechas(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (this.estadoReserva == EstadoReserva.FINALIZADA ||
            this.estadoReserva == EstadoReserva.CANCELADA)
            throw new IllegalStateException(
                "No se puede modificar una reserva FINALIZADA o CANCELADA");

        if (this.estadoReserva == EstadoReserva.EN_CURSO) {
            if (fechaEntrada != null && !fechaEntrada.equals(this.fechaEntrada))
                throw new IllegalStateException(
                    "No se puede modificar la fecha de entrada después del check-in");
            if (fechaSalida == null || !fechaSalida.isAfter(this.fechaEntrada))
                throw new IllegalStateException(
                    "La fecha de salida debe ser posterior a la fecha de entrada");
            this.fechaSalida = fechaSalida;
        } else {
            if (fechaEntrada == null || fechaSalida == null)
                throw new IllegalArgumentException("Las fechas son obligatorias");
            if (!fechaEntrada.isBefore(fechaSalida))
                throw new IllegalArgumentException(
                    "La fecha de entrada debe ser anterior a la de salida");
            this.fechaEntrada = fechaEntrada;
            this.fechaSalida = fechaSalida;
        }
    }

    public void eliminar() {
        if (this.estadoReserva == EstadoReserva.EN_CURSO)
            throw new IllegalStateException(
                "No se puede eliminar una reserva EN_CURSO");
        if (this.estadoRegistro == EstadoRegistro.ELIMINADO)
            throw new IllegalStateException("La reserva ya está eliminada");
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }
}