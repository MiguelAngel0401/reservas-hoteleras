package com.hotel.habitaciones.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hotel.habitaciones.dto.HabitacionRequest;
import com.hotel.habitaciones.dto.HabitacionResponse;
import com.hotel.habitaciones.services.HabitacionService;
import com.hotel.commons.controllers.CommonController;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/habitaciones")
@Slf4j
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }

    @PutMapping("/{id}/estado/{idEstado}")
    public ResponseEntity<HabitacionResponse> cambiarEstado(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @PathVariable @Positive(message = "El idEstado debe ser positivo") Integer idEstado) {
        
        log.info("Cambiando estado de la habitación {} al estado {}", id, idEstado);
        return ResponseEntity.ok(service.cambiarEstado(id, idEstado));
    }

    @GetMapping("/id-habitacion/{id}")
    public ResponseEntity<HabitacionResponse> obtenerHabitacionPorId(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id) {
        return ResponseEntity.ok(service.obtenerHabitacionPorId(id));
    }
}