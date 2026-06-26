package com.hotel.reservas.repositories;

import com.hotel.commons.enums.EstadoRegistro;
import com.hotel.commons.enums.EstadoReserva;
import com.hotel.reservas.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    Optional<Reserva> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    boolean existsByIdHuespedAndEstadoReserva(Long idHuesped, EstadoReserva estadoReserva);

    boolean existsByIdHabitacionAndEstadoReserva(Long idHabitacion, EstadoReserva estadoReserva);
    
}