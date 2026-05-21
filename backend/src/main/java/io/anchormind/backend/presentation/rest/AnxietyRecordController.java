package io.anchormind.backend.presentation.rest;

import io.anchormind.backend.business.facade.AnxietyRecordFacade;
import io.anchormind.backend.domain.dto.AnxietyRecordDTO;
import io.anchormind.backend.business.services.AIService;
import io.anchormind.backend.business.services.AnxietyRecordService;
import io.anchormind.backend.domain.dto.AnxietyRecordRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/anxiety-records")// La URL base
public class AnxietyRecordController {

    // Marcamos como 'final' para que RequiredArgsConstructor los inyecte automáticamente

    private final AnxietyRecordFacade recordFacade;

    public AnxietyRecordController(AnxietyRecordFacade recordFacade) {
        this.recordFacade = recordFacade;
    }

    // POST: Para recibir un nuevo registro desde el Front
    @PostMapping
    public ResponseEntity<AnxietyRecordDTO> createRecord(
            @Valid @RequestBody AnxietyRecordRequestDTO requestDTO,
            @RequestParam String username) {

        // El controlador solo le pide al servicio de registros que haga su trabajo
        AnxietyRecordDTO response = recordFacade.analyzeAndSave(requestDTO.getRawInput(), username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET: Para obtener todos los registros
    @GetMapping
    public ResponseEntity<List<AnxietyRecordDTO>> getAllRecords() throws Exception {

        List<AnxietyRecordDTO> records = recordFacade.findAllRecords();

        return ResponseEntity.ok(records);
    }

    // GET: Para obtener todos los registros de un paciente
    @GetMapping("/patient/{username}")
    public ResponseEntity<List<AnxietyRecordDTO>> getRecordsByPatient(@PathVariable String username) {

        List<AnxietyRecordDTO> records = recordFacade.findRecordsByPatient(username);
        return ResponseEntity.ok(records);
    }


    // GET: Para obtener todos los registros de una clínica
    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<AnxietyRecordDTO>> getRecordsByClinic(@PathVariable Long clinicId) {
        
        return ResponseEntity.ok(recordFacade.findRecordsByClinic(clinicId));
    }
}
