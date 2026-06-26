package com.hotel.huespedes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.huespedes.entities.Huesped;
import java.util.List;
import java.util.Optional;

import com.hotel.commons.enums.EstadoRegistro;


public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    List<Huesped> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    
    Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    boolean existsByEmailIgnoreCaseAndEstadoRegistro(String email, EstadoRegistro estadoRegistro);
    boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);
    boolean existsByDocumentoIgnoreCaseAndEstadoRegistro(String documento, EstadoRegistro estadoRegistro);
    
    boolean existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(String email, EstadoRegistro estadoRegistro, Long id);
    boolean existsByTelefonoAndEstadoRegistroAndIdNot(String telefono, EstadoRegistro estadoRegistro, Long id);
    boolean existsByDocumentoIgnoreCaseAndEstadoRegistroAndIdNot(String documento, EstadoRegistro estadoRegistro, Long id);
    

}
