package com.hotel.auth.services;

import java.util.Set;

import com.hotel.auth.dto.UsuarioRequest;
import com.hotel.auth.dto.UsuarioResponse;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);
}

