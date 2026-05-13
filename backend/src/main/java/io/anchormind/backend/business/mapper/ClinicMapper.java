package io.anchormind.backend.business.mapper;

import io.anchormind.backend.domain.dto.ClinicDTO;
import io.anchormind.backend.domain.entities.Clinic;
import org.springframework.stereotype.Component;

@Component
public class ClinicMapper {
    public ClinicDTO toDTO(Clinic clinic) {
        if (clinic == null) return null;

        // Mapeo manual
        return new ClinicDTO(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress(),
                clinic.getPhone(),
                clinic.isActive(),
                clinic.getCreatedAt());
    }

    public Clinic toEntity(ClinicDTO dto) {
        if (dto == null) return null;

        // Mapeo manual
        Clinic clinic = new Clinic();
        clinic.setId(dto.id());
        clinic.setName(dto.name());
        clinic.setAddress(dto.address());
        clinic.setPhone(dto.phone());
        clinic.setActive(dto.active() != null ? dto.active() : true);
        clinic.setCreatedAt(dto.createdAt());
        return clinic;
    }
}
