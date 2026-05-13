package io.anchormind.backend.business.services;

import io.anchormind.backend.business.services.base.BaseService;
import io.anchormind.backend.domain.dto.ClinicDTO;
import io.anchormind.backend.domain.entities.Clinic;

import java.util.List;

public interface ClinicService extends BaseService<Clinic, Long> {
    // Declaramos el método que devuelve el DTO limpio
    List<ClinicDTO> findAllActiveDTO();

    ClinicDTO findActiveByIdDTO(Long id) throws Exception;
    ClinicDTO findActiveByNameDTO(String name) throws Exception;
    ClinicDTO updateInstitutionalData(Long id, ClinicDTO dto) throws Exception;

    // Declaramos el método de creación que usa DTO
    ClinicDTO createClinic(ClinicDTO dto) throws Exception;
}
