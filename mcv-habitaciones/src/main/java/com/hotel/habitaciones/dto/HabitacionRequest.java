package com.hotel.habitaciones.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record HabitacionRequest(

		@NotNull(message = "El número de habitación es obligatorio")
		@Positive(message = "El número de habitación debe ser mayor a 0")
		@Max(value = 99999, message = "El número no puede tener más de 5 dígitos")
		Integer numero,

        @NotBlank(message = "El tipo de habitación es obligatorio")
        String tipo,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad mínima es 1")
        @Max(value = 5, message = "La capacidad máxima es 5 personas")
        Integer capacidad

) {}