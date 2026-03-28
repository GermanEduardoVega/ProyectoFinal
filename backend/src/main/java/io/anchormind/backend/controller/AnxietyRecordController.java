package io.anchormind.backend.controller;

import io.anchormind.backend.dto.AIAnalysisResponse;
import io.anchormind.backend.model.AnxietyRecord;
import io.anchormind.backend.repository.AnxietyRecordRepository;
//import io.anchormind.backend.service.AIService;
import io.anchormind.backend.service.AIServiceR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")// La URL base
public class    AnxietyRecordController {

    @Autowired
    private AnxietyRecordRepository repository;

    @Autowired
    private AIServiceR aiService;    // Inyectamos el servicio de IA

    // POST: Para recibir un nuevo registro desde el Front
    @PostMapping
    public AnxietyRecord createRecord(@RequestBody AnxietyRecord record,@RequestParam String username) {
        // 1. Llamamos a la IA pasándole el texto libre y el username
        return aiService.analyzeAndSaveForUser(record.getRawInput(), username);
    }

    // GET: Para ver todos los registros que hay en la base de datos
    @GetMapping
    public List<AnxietyRecord> getAllRecords() {
        return repository.findAll();
    }
}
