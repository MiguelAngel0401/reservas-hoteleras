package com.hotel.auth.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {
}