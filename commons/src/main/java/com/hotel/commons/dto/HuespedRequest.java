package com.hotel.commons.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HuespedRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    String nombre,
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    String apellido,
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser valido")
    @Size(min = 10, max = 100, message = "El email debe tener entre 10 y 100 caracteres")
    String email,
    
    @NotBlank(message = "El telefono es obligatorio")
    @Size(min = 10, max = 10, message = "El telefono debe tener 10 caracteres")
    String telefono,
    
    @NotBlank(message = "El documento es obligatorio")
    @Size(min = 7, max = 20, message = "El documento debe tener entre 7 y 20 caracteres")
    String documento,

    @NotBlank(message = "La nacionalidad es obligatoria")
    @Size(min = 3, max = 50, message = "La nacionalidad debe tener entre 3 y 50 caracteres")
    String nacionalidad
) {
}
