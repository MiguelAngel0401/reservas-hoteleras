package com.hotel.reservas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hotel.reservas.dto.ReservaRequest;
import com.hotel.reservas.dto.ReservaResponse;
import com.hotel.reservas.services.ReservaService;
import com.hotel.commons.controllers.CommonController;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/reservas")
@Slf4j
public class ReservaController extends CommonController<ReservaRequest, ReservaResponse, ReservaService> {

    public ReservaController(ReservaService service) {
        super(service);
    }

    @PatchMapping("/{idReserva}/estado/{idEstado}")
    public ResponseEntity<ReservaResponse> cambiarEstado(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long idReserva,
            @PathVariable @Positive(message = "El idEstado debe ser positivo") Integer idEstado) {

        log.info("Cambiando estado de la reserva {} al estado {}", idReserva, idEstado);
        return ResponseEntity.ok(service.cambiarEstado(idReserva, idEstado));
    }
}