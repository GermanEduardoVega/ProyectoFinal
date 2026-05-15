package io.anchormind.backend.business.services.impl;

import io.anchormind.backend.business.mapper.ClinicMapper;
import io.anchormind.backend.business.services.ClinicService;
import io.anchormind.backend.business.services.base.BaseServiceImpl;
import io.anchormind.backend.domain.dto.ClinicDTO;
import io.anchormind.backend.domain.entities.Clinic;
import io.anchormind.backend.repositories.ClinicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClinicServiceImpl extends BaseServiceImpl<Clinic, Long> implements ClinicService {

    // Inyectamos el repositorio específico para acceder a los métodos de búsqueda por estado
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public ClinicServiceImpl(ClinicRepository clinicRepository,ClinicMapper clinicMapper) {
        super(clinicRepository); // Pasamos el repo específico a la clase base
        this.clinicRepository = clinicRepository;
        this.clinicMapper = clinicMapper;
    }

    @Override
    @Transactional(readOnly = true) // La sesión de Hibernate se mantiene abierta para el mapeo
    public List<ClinicDTO> findAllActiveDTO() {
        // Usamos el nombre exacto que definiste en tu Repository: findAllByActiveTrue
        return clinicRepository.findAllByActiveTrue().stream()
                .map(clinicMapper::toDTO) // Convertimos a DTO plano antes de cerrar la sesión
                .toList();
    }


    // Método específico que maneja DTOs para el Controller
    @Override
    @Transactional
    public ClinicDTO createClinic(ClinicDTO dto) throws Exception {
        // Validación de nombre único entre las activas
        if (clinicRepository.findByNameIgnoreCaseAndActiveTrue(dto.name()).isPresent()) {
            throw new Exception("La clínica '" + dto.name() + "' ya se encuentra registrada.");
        }

        // Usamos el mapper para crear una entidad limpia
        Clinic clinic = clinicMapper.toEntity(dto);

        // El save devuelve la entidad con ID y CreatedAt generados por la DB
        return clinicMapper.toDTO(this.save(clinic));
    }

    @Override
    @Transactional( readOnly = true)
    public ClinicDTO findActiveByIdDTO(Long id) throws Exception {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new Exception("Clínica no encontrada"));
        return clinicMapper.toDTO(clinic);
    }

    @Override
    @Transactional(readOnly = true)
    public ClinicDTO findActiveByNameDTO(String name) throws Exception {
        Clinic clinic = clinicRepository.findByNameIgnoreCaseAndActiveTrue(name)
                .orElseThrow(() -> new Exception("Clínica no encontrada"));
        return clinicMapper.toDTO(clinic);
    }

    @Override
    @Transactional
    public ClinicDTO updateInstitutionalData(Long id, ClinicDTO dto) throws Exception {
        // 1. Buscamos la clínica existente (Estado gestionado por Hibernate)
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new Exception("No se encontró la clínica con ID: " + id));

        // 2. Usamos el método de actualización selectiva del Mapper
        clinicMapper.updateInstitutionalDataFromDTO(dto, clinic);

        // 3. Guardamos los cambios
        return clinicMapper.toDTO(clinicRepository.save(clinic));
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new Exception("Clínica no encontrada"));

        clinic.setActive(false); // Borrado lógico
        clinicRepository.save(clinic);
        return true;
    }
}
