package com.rnajankumarmandal.identity_service.service;

import com.rnajankumarmandal.identity_service.dto.*;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}