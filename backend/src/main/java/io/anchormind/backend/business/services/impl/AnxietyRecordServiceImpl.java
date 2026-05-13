package io.anchormind.backend.business.services.impl;

import io.anchormind.backend.business.services.AIService;
import io.anchormind.backend.business.services.base.BaseServiceImpl;
import io.anchormind.backend.domain.dto.AIAnalysisResponse;
import io.anchormind.backend.domain.dto.AnxietyRecordDTO;
import io.anchormind.backend.business.mapper.AnxietyRecordMapper;
import io.anchormind.backend.domain.entities.AnxietyRecord;
import io.anchormind.backend.domain.entities.User;
import io.anchormind.backend.repositories.AnxietyRecordRepository;
import io.anchormind.backend.repositories.ClinicRepository;
import io.anchormind.backend.repositories.UserRepository;
import io.anchormind.backend.business.services.AnxietyRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class AnxietyRecordServiceImpl extends BaseServiceImpl<AnxietyRecord, Long> implements AnxietyRecordService {

    private final AIService aiService; // Ahora inyectamos el servicio de IA
    private final AnxietyRecordRepository recordRepository;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;
    private final AnxietyRecordMapper recordMapper;

    // Constructor manual para inyectar dependencias y cumplir con la herencia
    public AnxietyRecordServiceImpl(AnxietyRecordRepository recordRepository,
                                    AIService aiService,
                                    ClinicRepository clinicRepository,
                                    UserRepository userRepository,
                                    AnxietyRecordMapper recordMapper) {
        super(recordRepository); // Le pasamos el repo base al BaseServiceImpl
        this.aiService = aiService;
        this.recordRepository = recordRepository;
        this.clinicRepository = clinicRepository;
        this.userRepository = userRepository;
        this.recordMapper = recordMapper;
    }

    @Override
    @Transactional
    public AnxietyRecordDTO analyzeAndSave(String userText, String username) {
        // A.0 Buscar usuario activo (Metadatos) en base de datos
        User user = userRepository.findActiveByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + username));
        // A.1 El texto ya viene sin formato JSON gracias al DTO, solo hacemos trim
       String cleanText = userText.trim();

        // B. Obtener análisis de la IA (PASANDOLE EL TEXTO LIBRE DEL USUARIO ya limpio)
        AIAnalysisResponse aiAnalysis = aiService.getAnalysisFromAI(cleanText);

        // C. Crear y persistir la entidad
        AnxietyRecord record = new AnxietyRecord();
        record.setUser(user);
        record.setRawInput(cleanText);
        record.setTimeStamp(LocalDateTime.now());

        // ... seteo de campos de aiAnalysis ...
        if (aiAnalysis != null) {
            record.setAnxietyLevel(aiAnalysis.getAnxietyLevel());
            record.setTriggerIdentified(aiAnalysis.getTriggerIdentified());
            record.setTechnique(aiAnalysis.getTechnique());
            record.setApplicability(aiAnalysis.getApplicability());
            record.setAwarenessMessage(aiAnalysis.getAwarenessMessage());
            record.setActionSteps(aiAnalysis.getActionSteps());

            // OPCIÓN RECOMENDADA: Guardá un resumen de todo el análisis en el campo JSON
            String resumen = String.format(
                    "Nivel: %d | Gatill o: %s | Técnica: %s",
                    aiAnalysis.getAnxietyLevel(),
                    aiAnalysis.getTriggerIdentified(),
                    aiAnalysis.getTechnique()
            );
            record.setAiResponseJson(resumen);
        }
        // D. Guardar y transformar a DTO
        return recordMapper.toDTO(recordRepository.save(record));
    }

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
        // 1. Buscamos al usuario activo (Validamos existencia y estado en un solo paso)
        User user = userRepository.findActiveByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("El usuario '" + username + "' no existe o está inactivo."));

        // 2. Ejecutamos la consulta JPQL por ID que es más rápida
        return recordRepository.findByUserId(user.getId())
                .stream()
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

