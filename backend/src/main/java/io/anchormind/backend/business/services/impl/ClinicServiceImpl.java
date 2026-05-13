package io.anchormind.backend.business.services.impl;

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

    public ClinicServiceImpl(ClinicRepository clinicRepository) {
        super(clinicRepository); // Pasamos el repo específico a la clase base
        this.clinicRepository = clinicRepository;
    }

    @Override
    @Transactional(readOnly = true) // La sesión de Hibernate se mantiene abierta para el mapeo
    public List<ClinicDTO> findAllActiveDTO() {
        // Usamos el nombre exacto que definiste en tu Repository: findAllByActiveTrue
        return clinicRepository.findAllByActiveTrue().stream()
                .map(this::toDTO) // Convertimos a DTO plano antes de cerrar la sesión
                .toList();
    }


    // Método específico que maneja DTOs para el Controller
    @Override
    @Transactional
    public ClinicDTO createClinic(ClinicDTO dto) throws Exception {
        // Nivel 1: Validación contra la DB (Nombre único)
        if (clinicRepository.findByNameIgnoreCaseAndActiveTrue(dto.name()).isPresent()) {
            throw new Exception("La clínica '" + dto.name() + "' ya se encuentra registrada.");
        }

        // Nivel 2: Si recibieras una lista (HashSet), esto asegura que no haya duplicados en el lote
        // Set<ClinicDTO> clinicSet = new HashSet<>(listaRecibida);

        Clinic clinic = Clinic.builder()
                .name(dto.name())
                .address(dto.address())
                .phone(dto.phone())
                .active(true)
                .build();

        return toDTO(this.save(clinic));
    }

    // Mapper manual que evita cargar la colección perezosa de usuarios
    private ClinicDTO toDTO(Clinic clinic) {
        return new ClinicDTO(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress(),
                clinic.getPhone(),
                clinic.isActive(),
                clinic.getCreatedAt()
        );
    }

    @Override
    @Transactional( readOnly = true)
    public ClinicDTO findActiveByIdDTO(Long id) throws Exception {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new Exception("Clínica no encontrada"));
        return toDTO(clinic);
    }

    @Override
    @Transactional(readOnly = true)
    public ClinicDTO findActiveByNameDTO(String name) throws Exception {
        Clinic clinic = clinicRepository.findByNameIgnoreCaseAndActiveTrue(name)
                .orElseThrow(() -> new Exception("Clínica no encontrada"));
        return toDTO(clinic);
    }

    @Override
    @Transactional
    public ClinicDTO updateInstitutionalData(Long id, ClinicDTO dto) throws Exception {
        // 1. Buscamos la clínica existente
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new Exception("No se encontró la clínica con ID: " + id));

        // 2. Actualizamos solo los campos permitidos
        clinic.setName(dto.name());
        clinic.setAddress(dto.address());
        clinic.setPhone(dto.phone());

        // 3. Guardamos y devolvemos el DTO
        return toDTO(clinicRepository.save(clinic));
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        // Usamos clinicRepository. Ambos apuntan al mismo bean de Spring,
        // pero el específico es más coherente con tu nueva arquitectura.
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new Exception("Clínica no encontrada"));

        clinic.setActive(false); // Tu lógica de borrado lógico se mantiene intacta
        clinicRepository.save(clinic);
        return true;
    }
}
