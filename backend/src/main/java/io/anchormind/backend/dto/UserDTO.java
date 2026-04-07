package io.anchormind.backend.dto;

public record UserDTO(
        Long id,
        String username,
        String role,
        String clinicName

        ) {}
