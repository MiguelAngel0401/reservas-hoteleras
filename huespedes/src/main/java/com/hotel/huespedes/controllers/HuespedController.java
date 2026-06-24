package com.hotel.huespedes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotel.commons.controllers.CommonController;
import com.hotel.commons.dto.HuespedRequest;
import com.hotel.commons.dto.HuespedResponse;
import com.hotel.huespedes.services.HuespedService;

import jakarta.validation.constraints.Positive;

@Controller
@RequestMapping("/api/huespedes")
@Validated
public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService> {
    public HuespedController(HuespedService service) {
        super(service);
    }

    @GetMapping("/{id-medico}/{id}")
    public ResponseEntity<HuespedResponse> obtenerHuespedPorIdSinEstado(
        @PathVariable @Positive(message = "El id debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(service.obtenerHuespedPorIdSinEstado(id));
    }
}
