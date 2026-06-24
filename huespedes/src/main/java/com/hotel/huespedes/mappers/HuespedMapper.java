package com.hotel.huespedes.mappers;

import org.springframework.stereotype.Component;

import com.hotel.commons.dto.HuespedRequest;
import com.hotel.commons.dto.HuespedResponse;
import com.hotel.commons.mappers.CommonMapper;
import com.hotel.huespedes.entities.Huesped;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {

    @Override
    public HuespedResponse entidadAResponse(Huesped entidad) {
        if (entidad == null) return null;

        return new HuespedResponse(
            entidad.getId(),
            entidad.getNombre(),
            entidad.getApellido(),
            entidad.getEmail(),
            entidad.getTelefono(),
            entidad.getDocumento(),
            entidad.getNacionalidad()
        );
    }

    @Override
    public Huesped requestAEntidad(HuespedRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'requestAEntidad'");
    }
}
