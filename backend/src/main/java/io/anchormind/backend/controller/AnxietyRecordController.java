package io.anchormind.backend.controller;

import io.anchormind.backend.dto.AnxietyRecordDTO;
import io.anchormind.backend.service.AIService;
import io.anchormind.backend.service.AnxietyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")// La URL base
@RequiredArgsConstructor
public class AnxietyRecordController {

    // Marcamos como 'final' para que RequiredArgsConstructor los inyecte automáticamente
    private final AnxietyRecordService recordService; // Final = Inmutable
    private final AIService aiService;


    // POST: Para recibir un nuevo registro desde el Front
    @PostMapping
    public ResponseEntity<AnxietyRecordDTO> createRecord(
            @RequestBody String rawInput,
            @RequestParam String username) {
        // Llamamos al servicio de IA (que internamente guarda y devuelve el DTO)
        AnxietyRecordDTO savedRecord = aiService.analyzeAndSaveForUser(rawInput, username);
        return ResponseEntity.ok(savedRecord);
    }

    // GET: Para obtener todos los registros
    @GetMapping
    public ResponseEntity<List<AnxietyRecordDTO>> getAllRecords() {
        List<AnxietyRecordDTO> records = recordService.findAllRecords();

        return ResponseEntity.ok(records);
    }
    // GET: Para obtener todos los registros de un paciente
    @GetMapping("/patient/{username}")
    public ResponseEntity<List<AnxietyRecordDTO>> getRecordsByPatient(@PathVariable String username) {
        return ResponseEntity.ok(recordService.findRecordsByPatient(username));
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<AnxietyRecordDTO>> getRecordsByClinic(@PathVariable Long clinicId) {
        return ResponseEntity.ok(recordService.findRecordsByClinic(clinicId));
    }
}
