package com.rnajankumarmandal.identity_service.controller;

import com.rnajankumarmandal.identity_service.dto.RegisterRequest;
import com.rnajankumarmandal.identity_service.dto.RegisterResponse;
import com.rnajankumarmandal.identity_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }
}