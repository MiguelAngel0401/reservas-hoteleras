package com.hotel.huespedes.entities;

import com.hotel.commons.enums.EstadoRegistro;
import com.hotel.commons.utils.StringCustomUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HUESPEDES")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Huesped {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HUESPED")
    private Long id;

    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @Column(name = "APELLIDO", length = 50, nullable = false)
    private String apellido;
    
    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;
    
    @Column(name = "TELEFONO", length = 10, nullable = false, unique = true)
    private String telefono;
    
    @Column(name = "DOCUMENTO", length = 20, nullable = false, unique = true)
    private String documento;
    
    @Column(name = "NACIONALIDAD", length = 50, nullable = false)
    private String nacionalidad;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", length = 15, nullable = false)
    private EstadoRegistro estadoRegistro;

    public static Huesped crear(String nombre, String apellido, String email, String telefono, String documento, String nacionalidad) {
        String nombreLimpio = nombre != null ? nombre.trim() : null;
        String apellidoLimpio = apellido != null ? apellido.trim() : null;
        String emailLimpio = email != null ? email.trim() : null;
        String telefonoLimpio = telefono != null ? telefono.trim() : null;
        String documentoLimpio = documento != null ? documento.trim().toUpperCase() : null;
        String nacionalidadLimpio = nacionalidad != null ? nacionalidad.trim() : null;

        validarDatos(nombreLimpio, apellidoLimpio, emailLimpio, telefonoLimpio, documentoLimpio, nacionalidadLimpio);

        return Huesped.builder()
                .nombre(nombreLimpio)
                .apellido(apellidoLimpio)
                .email(emailLimpio)
                .telefono(telefonoLimpio)
                .documento(documentoLimpio)
                .nacionalidad(nacionalidadLimpio)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    public void actualizar(String nombre, String apellido, String email, String telefono, String documento, String nacionalidad) {
        validarNoEliminado();
        String nombreLimpio = nombre != null ? nombre.trim() : null;
        String apellidoLimpio = apellido != null ? apellido.trim() : null;
        String emailLimpio = email != null ? email.trim() : null;
        String telefonoLimpio = telefono != null ? telefono.trim() : null;
        String documentoLimpio = documento != null ? documento.trim().toUpperCase() : null;
        String nacionalidadLimpio = nacionalidad != null ? nacionalidad.trim() : null;

        validarDatos(nombreLimpio, apellidoLimpio, emailLimpio, telefonoLimpio, documentoLimpio, nacionalidadLimpio);

        this.nombre = nombreLimpio;
        this.apellido = apellidoLimpio;
        this.email = emailLimpio;
        this.telefono = telefonoLimpio;
        this.documento = documentoLimpio;
        this.nacionalidad = nacionalidadLimpio;
    }

    public void eliminar() {
        validarNoEliminado();
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    private static void validarDatos(String nombre, String apellido, String email, String telefono, String documento, String nacionalidad) {
        StringCustomUtils.validarTamanio(nombre, 2, 50, "El nombre debe tener entre 3 y 50 caracteres");
        StringCustomUtils.validarTamanio(apellido, 2, 50, "El apellido debe tener entre 3 y 50 caracteres");
        StringCustomUtils.validarTamanio(email, 10, 100, "El email debe tener entre 10 y 100 caracteres");
        StringCustomUtils.validarTamanio(telefono, 10, 10, "El telefono debe tener 10 caracteres");
        StringCustomUtils.validarTamanio(documento, 7, 20, "El documento debe tener entre 7 y 20 caracteres");
        StringCustomUtils.validarTamanio(nacionalidad, 3, 50, "La nacionalidad debe tener entre 3 y 50 caracteres");
    }

    private void validarNoEliminado() {
        if (this.estadoRegistro == EstadoRegistro.ELIMINADO) {
            throw new IllegalArgumentException("El huesped ya esta eliminado");
        }
    }
}
