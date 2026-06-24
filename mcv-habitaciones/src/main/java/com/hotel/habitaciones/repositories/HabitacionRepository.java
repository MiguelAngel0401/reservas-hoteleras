package com.hotel.habitaciones.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotel.habitaciones.entities.Habitacion;
import com.hotel.habitaciones.enums.EstadoRegistro;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    Optional<Habitacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
    boolean existsByNumeroAndEstadoRegistro(Integer numero, EstadoRegistro estadoRegistro);
    boolean existsByNumeroAndEstadoRegistroAndIdNot(Integer numero, EstadoRegistro estadoRegistro, Long id);
}