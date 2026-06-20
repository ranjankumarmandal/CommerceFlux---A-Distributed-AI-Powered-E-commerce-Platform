package com.rnajankumarmandal.identity_service.service;

import com.rnajankumarmandal.identity_service.dto.RegisterRequest;
import com.rnajankumarmandal.identity_service.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);
}