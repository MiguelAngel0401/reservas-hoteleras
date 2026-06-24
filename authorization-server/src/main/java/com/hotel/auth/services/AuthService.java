package com.hotel.auth.services;

import com.hotel.auth.dto.LoginRequest;
import com.hotel.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}