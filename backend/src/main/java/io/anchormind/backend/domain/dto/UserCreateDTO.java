package io.anchormind.backend.domain.dto;

import io.anchormind.backend.domain.enums.Role;

public record UserCreateDTO(
        String username,
        String password,
        String fullName,
        String email,
        Role role // Ej: "PATIENT" o "ADMIN"
) {}
