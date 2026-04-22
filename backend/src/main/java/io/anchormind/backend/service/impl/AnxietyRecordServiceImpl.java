package io.anchormind.backend.service.impl;

import io.anchormind.backend.dto.AnxietyRecordDTO;
import io.anchormind.backend.mapper.AnxietyRecordMapper;
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
    private final AnxietyRecordMapper recordMapper;


    @Override
    @Transactional(readOnly = true) // Mantiene la sesión abierta para cargar los Lazy
    public List<AnxietyRecordDTO> findAllRecords() {
        return recordRepository.findAll().stream()
                .map(recordMapper::toDTO)
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
                .map(recordMapper::toDTO)
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
                .map(recordMapper::toDTO).toList();
    }






}

