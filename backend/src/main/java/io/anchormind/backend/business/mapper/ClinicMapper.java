package io.anchormind.backend.business.mapper;

import io.anchormind.backend.domain.dto.ClinicDTO;
import io.anchormind.backend.domain.entities.Clinic;
import org.springframework.stereotype.Component;

@Component
public class ClinicMapper {

    // 1. Para mostrar datos (GET)
    public ClinicDTO toDTO(Clinic clinic) {
        if (clinic == null) return null;
        return new ClinicDTO(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress(),
                clinic.getPhone(),
                clinic.isActive(),
                clinic.getCreatedAt());
    }

    // 2. Para crear datos (POST) - Ignora IDs y fechas de creación
    public Clinic toEntity(ClinicDTO dto) {
        if (dto == null) return null;
        Clinic clinic = new Clinic();
        clinic.setName(dto.name());
        clinic.setAddress(dto.address());
        clinic.setPhone(dto.phone());
        clinic.setActive(true); // Siempre nace activa
        return clinic;
    }

    // 3. LA SOLUCIÓN: Para actualizaciones institucionales (PUT)
    // Recibe la entidad que YA ESTÁ en la base de datos y la actualiza
    public void updateInstitutionalDataFromDTO(ClinicDTO dto, Clinic existingClinic) {
        if (dto == null || existingClinic == null) return;

        // Solo pisamos los campos que permitimos cambiar
        existingClinic.setName(dto.name());
        existingClinic.setAddress(dto.address());
        existingClinic.setPhone(dto.phone());

        // EL ID Y EL CREATED_AT NO SE TOCAN AQUÍ
    }
}