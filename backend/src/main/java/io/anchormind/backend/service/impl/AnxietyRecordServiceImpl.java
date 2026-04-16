package io.anchormind.backend.service.impl;

import io.anchormind.backend.dto.AnxietyRecordDTO;
import io.anchormind.backend.dto.UserDTO;
import io.anchormind.backend.model.entity.AnxietyRecord;
import io.anchormind.backend.repository.AnxietyRecordRepository;
import io.anchormind.backend.repository.ClinicRepository;
import io.anchormind.backend.repository.UserRepository;
import io.anchormind.backend.service.AnxietyRecordService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnxietyRecordServiceImpl implements AnxietyRecordService {

    private final AnxietyRecordRepository recordRepository;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional(readOnly = true) // Mantiene la sesión abierta para cargar los Lazy
    public List<AnxietyRecordDTO> findAllRecords() {
        return recordRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnxietyRecordDTO> findRecordsByPatient(String username) {
        // 1. VALIDACIÓN (Usando tu método del repo de usuarios)
        if (userRepository.findActiveByUsername(username).isEmpty()) {
            throw new EntityNotFoundException("El usuario '" + username + "' no existe o está inactivo.");
        }

        // 2. LÓGICA
        return recordRepository.findAllByUsername(username).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnxietyRecordDTO> findRecordsByClinic(Long clinicId) {
        // 1. Validar que la clínica exista
        if (!clinicRepository.existsById(clinicId)) {
            throw new EntityNotFoundException("La clínica con ID " + clinicId + " no existe.");
        }
        // 2. Si existe, usamos el nuevo método de JPQL
        return recordRepository.findAllByClinicId(clinicId).stream()
                .map(this::mapToDTO).toList();
    }


    private AnxietyRecordDTO mapToDTO(AnxietyRecord entity) {
        // Extraemos los datos del usuario y su clínica de forma segura
        String clinicName = (entity.getUser().getClinic() != null)
                ? entity.getUser().getClinic().getName()
                : "Sin Clínica Asignada";

        //Mapeo del Usuario
        UserDTO userDTO = new UserDTO(
                entity.getUser().getId(),
                entity.getUser().getUsername(),
                entity.getUser().getRole().toString(),
                clinicName
        );

        // 4. Construimos el DTO final con TODOS tus campos actuales
        return new AnxietyRecordDTO(
                entity.getId(),
                entity.getTimeStamp(),
                entity.getRawInput(),
                entity.getAnxietyLevel(),
                entity.getTriggerIdentified(),
                entity.getTechnique(),
                entity.getApplicability(),
                entity.getAwarenessMessage(),
                entity.getActionSteps(),
                userDTO
        );


    }
}

