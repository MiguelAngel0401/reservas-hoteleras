package com.hotel.auth.dto;

public record ErrorResponse(
        int codigo,
        String mensaje
) { }

