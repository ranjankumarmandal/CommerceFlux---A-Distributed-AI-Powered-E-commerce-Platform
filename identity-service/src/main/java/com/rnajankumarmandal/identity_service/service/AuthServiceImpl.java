package com.rnajankumarmandal.identity_service.service;

import com.rnajankumarmandal.identity_service.dto.*;
import com.rnajankumarmandal.identity_service.entity.User;
import com.rnajankumarmandal.identity_service.exception.DuplicateResourceException;
import com.rnajankumarmandal.identity_service.exception.UnauthorizedException;
import com.rnajankumarmandal.identity_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(User.Role.ROLE_CUSTOMER)
                .status(User.UserStatus.ACTIVE)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        repository.save(user);

        return new RegisterResponse(user.getId(), user.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}