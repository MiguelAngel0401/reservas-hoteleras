package com.hotel.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.hotel.commons.configurations.FeignClientConfig;

@FeignClient(name = "mcv-reservas", configuration = FeignClientConfig.class)
public interface ReservaClient {
    @GetMapping("/api/reservas/huesped/{idHuesped}/en-curso")
    Boolean tieneReservaEnCurso(@PathVariable Long idHuesped);
}