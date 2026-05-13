package io.anchormind.backend.domain.dto;

import java.time.LocalDateTime;

public record ClinicDTO(
        Long id,
        String name,
        String address,
        String phone,
        Boolean active,
        LocalDateTime createdAt

) {}
