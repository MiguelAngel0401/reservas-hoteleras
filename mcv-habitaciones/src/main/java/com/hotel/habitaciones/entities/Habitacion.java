package com.hotel.habitaciones.entities;

import com.hotel.habitaciones.enums.EstadoHabitacion;
import com.hotel.habitaciones.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "HABITACIONES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder @Getter
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION")
    private Long id;

    @Column(name = "NUMERO", nullable = false)
    private Integer numero;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @Column(name = "PRECIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @Column(name = "ESTADO_HABITACION", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoHabitacion estadoHabitacion;

    @Column(name = "ESTADO_REGISTRO", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoRegistro estadoRegistro;

    public static Habitacion crear(Integer numero, String tipo, BigDecimal precio, Integer capacidad) {
        return Habitacion.builder()
                .numero(numero)
                .tipo(tipo)
                .precio(precio)
                .capacidad(capacidad)
                .estadoHabitacion(EstadoHabitacion.DISPONIBLE)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    public void actualizar(Integer numero, String tipo, BigDecimal precio, Integer capacidad) {
        validarNoEliminada();
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
    }

    public void cambiarEstado(EstadoHabitacion nuevoEstado) {
        validarNoEliminada();
        if (nuevoEstado == null)
            throw new IllegalStateException("El nuevo estado es requerido");
        if (!this.estadoHabitacion.puedeCambiarA(nuevoEstado))
            throw new IllegalStateException("La habitación con estado " + this.estadoHabitacion.name() +
                    " no puede cambiar a " + nuevoEstado.name());
        this.estadoHabitacion = nuevoEstado;
    }

    public void cambiarEstadoSistema(EstadoHabitacion nuevoEstado) {
        validarNoEliminada();
        this.estadoHabitacion = nuevoEstado;
    }

    public void eliminar() {
        validarNoEliminada();
        if (this.estadoHabitacion == EstadoHabitacion.OCUPADA)
            throw new IllegalStateException("No se puede eliminar una habitación OCUPADA");
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    private void validarNoEliminada() {
        if (this.estadoRegistro == EstadoRegistro.ELIMINADO)
            throw new IllegalStateException("La habitación ya está eliminada");
    }
}