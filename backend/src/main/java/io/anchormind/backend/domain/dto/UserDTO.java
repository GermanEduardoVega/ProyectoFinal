package io.anchormind.backend.domain.dto;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String username,
        String fullName,
        String email,
        String role,
        Long clinicId,
        String clinicName,
        boolean active,
        LocalDateTime createdAt // Nuevo: Auditoría básica

        ) {

        public UserDTO(Long id, String username, String fullName, String email, String role, Long clinicId, String clinicName, boolean active, LocalDateTime createdAt) {
                this.id = id;
                this.username = username;
                this.fullName = fullName;
                this.email = email;
                this.role = role;
                this.clinicId = clinicId;
                this.clinicName = clinicName;
                this.active = active;
                this.createdAt = createdAt;

        }
}
