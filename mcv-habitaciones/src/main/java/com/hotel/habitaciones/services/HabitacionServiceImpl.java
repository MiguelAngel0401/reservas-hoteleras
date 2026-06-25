package com.hotel.habitaciones.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hotel.commons.exceptions.RecursoNoEncontradoException;
import com.hotel.habitaciones.dto.HabitacionRequest;
import com.hotel.habitaciones.dto.HabitacionResponse;
import com.hotel.habitaciones.entities.Habitacion;
import com.hotel.habitaciones.enums.EstadoHabitacion;
import com.hotel.habitaciones.enums.EstadoRegistro;
import com.hotel.habitaciones.mappers.HabitacionMapper;
import com.hotel.habitaciones.repositories.HabitacionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        log.info("Listando habitaciones activas");
        return habitacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream().map(habitacionMapper::entidadAResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(Long id) {
        log.info("Buscando habitación id {}", id);
        return habitacionMapper.entidadAResponse(obtenerEntidadActiva(id));
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerHabitacionPorId(Long id) {
        return habitacionMapper.entidadAResponse(
            habitacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "Habitación no encontrada con id: " + id))
        );
    }

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando habitación número {}", request.numero());
        if (habitacionRepository.existsByNumeroAndEstadoRegistro(
                request.numero(), EstadoRegistro.ACTIVO))
            throw new IllegalStateException("Ya existe una habitación activa con el número " + request.numero());
        Habitacion habitacion = habitacionMapper.requestAEntidad(request);
        return habitacionMapper.entidadAResponse(habitacionRepository.save(habitacion));
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        log.info("Actualizando habitación id {}", id);
        Habitacion habitacion = obtenerEntidadActiva(id);
        if (habitacionRepository.existsByNumeroAndEstadoRegistroAndIdNot(
                request.numero(), EstadoRegistro.ACTIVO, id))
            throw new IllegalStateException("Ya existe una habitación activa con el número " + request.numero());
        habitacion.actualizar(request.numero(), request.tipo(), request.precio(), request.capacidad());
        return habitacionMapper.entidadAResponse(habitacionRepository.save(habitacion));
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando habitación id {}", id);
        Habitacion habitacion = obtenerEntidadActiva(id);
        habitacion.eliminar();
        habitacionRepository.save(habitacion);
    }

    @Override
    public HabitacionResponse cambiarEstado(Long id, Integer idEstado) {
        log.info("Cambiando estado habitación id {} a estado {}", id, idEstado);
        Habitacion habitacion = obtenerEntidadActiva(id);
        EstadoHabitacion nuevoEstado = EstadoHabitacion.obtenerPorCodigo(idEstado.longValue());
        if (habitacion.getEstadoHabitacion() == EstadoHabitacion.OCUPADA
                && nuevoEstado == EstadoHabitacion.DISPONIBLE)
            throw new IllegalStateException("No se puede cambiar manualmente a DISPONIBLE una habitación OCUPADA");
        habitacion.cambiarEstado(nuevoEstado);
        return habitacionMapper.entidadAResponse(habitacionRepository.save(habitacion));
    }

    private Habitacion obtenerEntidadActiva(Long id) {
        return habitacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Habitación no encontrada con id: " + id));
    }
    
    @Override
    public HabitacionResponse actualizarEstadoInterno(Long id, Integer idEstado) {
        log.info("Cambio de estado interno habitación id {} a estado {}", id, idEstado);
        Habitacion habitacion = obtenerEntidadActiva(id);
        EstadoHabitacion nuevoEstado = EstadoHabitacion.obtenerPorCodigo(idEstado.longValue());
        
        habitacion.cambiarEstado(nuevoEstado);
        return habitacionMapper.entidadAResponse(habitacionRepository.save(habitacion));
    }
}