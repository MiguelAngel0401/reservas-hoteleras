package com.hotel.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.hotel.commons.configurations.FeignClientConfig;
import com.hotel.commons.dto.HabitacionResponse;

@FeignClient(name = "mcv-habitaciones", configuration = FeignClientConfig.class)
public interface HabitacionClient {

    @GetMapping("/api/habitaciones/{id}")
    HabitacionResponse obtenerHabitacionActivaPorId(@PathVariable Long id);

    @GetMapping("/api/habitaciones/id-habitacion/{id}")
    HabitacionResponse obtenerHabitacionSinEstadoPorId(@PathVariable Long id);

    @PutMapping("/api/habitaciones/{id}/estado/{idEstado}")
    HabitacionResponse cambiarEstado(@PathVariable Long id,
                                     @PathVariable Integer idEstado);
    
    @PutMapping("/api/habitaciones/{id}/estado-interno/{idEstado}")
    HabitacionResponse actualizarEstadoInterno(@PathVariable Long id, @PathVariable Integer idEstado);
}