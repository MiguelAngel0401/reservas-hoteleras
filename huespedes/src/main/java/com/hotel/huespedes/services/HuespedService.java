package com.hotel.huespedes.services;

import com.hotel.commons.dto.HuespedRequest;
import com.hotel.commons.dto.HuespedResponse;
import com.hotel.commons.services.CrudService;

public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse> {
    HuespedResponse obtenerHuespedPorIdSinEstado(Long id);
}
