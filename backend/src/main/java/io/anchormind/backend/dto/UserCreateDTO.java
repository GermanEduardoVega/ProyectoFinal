package io.anchormind.backend.dto;

import io.anchormind.backend.model.enums.Role;

public record UserCreateDTO(
        String username,
        String password,
        String fullName,
        String email,
        Role role // Ej: "PATIENT" o "ADMIN"
) {}
