package com.hotel.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.hotel.commons.configurations.FeignClientConfig;
import com.hotel.commons.dto.HuespedResponse;

@FeignClient(name = "huespedes", configuration = FeignClientConfig.class)
public interface HuespedClient {

    @GetMapping("/api/huespedes/{id}")
    HuespedResponse obtenerHuespedActivoPorId(@PathVariable Long id);

    @GetMapping("/api/huespedes/id-huesped/{id}")
    HuespedResponse obtenerHuespedSinEstadoPorId(@PathVariable Long id);
}