package com.hotel.huespedes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.commons.dto.HuespedRequest;
import com.hotel.commons.dto.HuespedResponse;
import com.hotel.commons.enums.EstadoRegistro;
import com.hotel.commons.exceptions.RecursoNoEncontradoException;
import com.hotel.huespedes.entities.Huesped;
import com.hotel.huespedes.mappers.HuespedMapper;
import com.hotel.huespedes.repositories.HuespedRepository;

import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HuespedServiceImpl implements HuespedService {

    private final HuespedMapper huespedMapper;
    private final HuespedRepository huespedRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<HuespedResponse> listar() {
        log.info("Listando todos los huespedes");
        return huespedRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
            .stream()
            .map(huespedMapper::entidadAResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerPorId(Long id) {
        log.info("Obteniendo huesped activo por id: {}", id);
        Huesped huesped = obtenerHuespedActivoPorId(id);
        return huespedMapper.entidadAResponse(huesped);
    }

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        log.info("Registrando nuevo huesped: {}", request);
        validarDatosUnicos(request.email(), request.telefono(), request.documento());
        Huesped huesped = Huesped.crear(
                request.nombre(), 
                request.apellido(), 
                request.email(), 
                request.telefono(), 
                request.documento(), 
                request.nacionalidad());
        Huesped huespedGuardado = huespedRepository.save(huesped);
        log.info("Huesped registrado exitosamente: {}", huespedGuardado);
        return huespedMapper.entidadAResponse(huespedGuardado);
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        log.info("Actualizando paciente con id: {}", id);
        Huesped huesped = obtenerHuespedActivoPorId(id);
        validarCambiosEnDatosUnicos(id, request.email(), request.telefono(), request.documento());
        huesped.actualizar(
                request.nombre(), 
                request.apellido(), 
                request.email(), 
                request.telefono(), 
                request.documento(), 
                request.nacionalidad());
        Huesped huespedActualizado = huespedRepository.save(huesped);
        log.info("Huesped actualizado exitosamente: {}", huespedActualizado);
        return huespedMapper.entidadAResponse(huespedActualizado);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando huesped con id: {}", id);
        Huesped huesped = obtenerHuespedActivoPorId(id);
        huesped.eliminar();
        log.info("Paciente con id {} eliminado exitosamente", id);
    }

    private Huesped obtenerHuespedActivoPorId(Long id) {
        log.info("Buscando huesped activo por id: {}", id);
        return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro ningun huesped activo con el id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerHuespedPorIdSinEstado(Long id) {
        log.info("Obteniendo huesped por id sin estado: {}", id);
        return huespedMapper.entidadAResponse(huespedRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro ningun huesped con el id: " + id)));
    }

    private void validarDatosUnicos(String email, String telefono, String documento) {
       log.info("Validando email unico");
       if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistro(email.trim(), EstadoRegistro.ACTIVO)) {
        throw new IllegalStateException("Ya existe un huesped activo con el email: " + email);
       }

       log.info("Validando telefono unico");
       if (huespedRepository.existsByTelefonoAndEstadoRegistro(telefono.trim(), EstadoRegistro.ACTIVO)) {
        throw new IllegalStateException("Ya existe un huesped activo con el telefono: " + telefono);
       }

       log.info("Validando documento unico");
       if (huespedRepository.existsByDocumentoIgnoreCaseAndEstadoRegistro(documento.trim(), EstadoRegistro.ACTIVO)) {
        throw new IllegalStateException("Ya existe un huesped activo con el documento: " + documento);
       }
    }

    private void validarCambiosEnDatosUnicos(Long id, String email, String telefono, String documento) {
        if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(email.trim(), EstadoRegistro.ACTIVO, id)) {
            throw new IllegalStateException("Ya existe otro huesped con el mismo email");
        }
        if (huespedRepository.existsByTelefonoAndEstadoRegistroAndIdNot(telefono.trim(), EstadoRegistro.ACTIVO, id)) {
            throw new IllegalStateException("Ya existe otro huesped con el mismo telefono");
        }
        if (huespedRepository.existsByDocumentoIgnoreCaseAndEstadoRegistroAndIdNot(documento.trim(), EstadoRegistro.ACTIVO, id)) {
            throw new IllegalStateException("Ya existe otro huesped con el mismo documento");
        }
    }
}
