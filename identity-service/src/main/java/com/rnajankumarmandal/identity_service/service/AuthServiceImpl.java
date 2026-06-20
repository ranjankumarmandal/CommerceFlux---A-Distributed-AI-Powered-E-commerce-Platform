package com.rnajankumarmandal.identity_service.service;

import com.rnajankumarmandal.identity_service.dto.RegisterRequest;
import com.rnajankumarmandal.identity_service.dto.RegisterResponse;
import com.rnajankumarmandal.identity_service.entity.Role;
import com.rnajankumarmandal.identity_service.entity.User;
import com.rnajankumarmandal.identity_service.entity.UserStatus;
import com.rnajankumarmandal.identity_service.exception.DuplicateResourceException;
import com.rnajankumarmandal.identity_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "Email already registered"
            );
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(request.email())
                .password(
                        passwordEncoder.encode(
                                request.password()
                        )
                )
                .role(Role.ROLE_CUSTOMER)
                .status(UserStatus.ACTIVE)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        userRepository.save(user);

        return new RegisterResponse(
                user.getId(),
                user.getEmail()
        );
    }
}