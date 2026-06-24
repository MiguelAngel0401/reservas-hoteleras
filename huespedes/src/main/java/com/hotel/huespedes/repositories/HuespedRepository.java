package com.hotel.huespedes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.huespedes.entities.Huesped;
import java.util.List;
import java.util.Optional;

import com.hotel.commons.enums.EstadoRegistro;


public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    // Obtiene todos los huespedes activos
    List<Huesped> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    
    // Obtiene un huesped activo por id
    Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    // Para validar datos durante creacion
    boolean existsByEmailIgnoreCaseAndEstadoRegistro(String email, EstadoRegistro estadoRegistro);
    boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);
    boolean existsByDocumentoIgnoreCaseAndEstadoRegistro(String documento, EstadoRegistro estadoRegistro);
    
    // Para validar datos durante actualizacion
    boolean existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(String email, EstadoRegistro estadoRegistro, Long id);
    boolean existsByTelefonoAndEstadoRegistroAndIdNot(String telefono, EstadoRegistro estadoRegistro, Long id);
    boolean existsByDocumentoIgnoreCaseAndEstadoRegistroAndIdNot(String documento, EstadoRegistro estadoRegistro, Long id);
    

}
