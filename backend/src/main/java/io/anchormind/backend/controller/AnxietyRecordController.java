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
        public AnxietyRecord createRecord(@RequestBody AnxietyRecord record) {


        // 1. Llamamos a la IA pasándole el texto libre
        AIAnalysisResponse aiAnalysis = (AIAnalysisResponse) aiService.getAnalysisFromAI(record.getRawInput());
        // 2. Por ahora, guardamos el resultado de la IA en el campo JSON
        //record.setAiResponseJson(aiResult);

        if (aiAnalysis != null) {
            record.setAnxietyLevel(aiAnalysis.getAnxietyLevel());
            record.setTriggerIdentified(aiAnalysis.getTriggerIdentified());
            record.setTechnique(aiAnalysis.getTechnique());
            record.setApplicability(aiAnalysis.getApplicability());

            // OPCIÓN RECOMENDADA: Guardá un resumen de todo el análisis en el campo JSON
            String resumen = String.format(
                    "Nivel: %d | Gatillo: %s | Técnica: %s",
                    aiAnalysis.getAnxietyLevel(),
                    aiAnalysis.getTriggerIdentified(),
                    aiAnalysis.getTechnique()
            );
            record.setAiResponseJson(resumen);
        }
        // 3. Guardamos en la DB
        return repository.save(record);
    }


    // GET: Para ver todos los registros que hay en la base de datos
    @GetMapping
        public List<AnxietyRecord> getAllRecords() {
        return repository.findAll();
    }
}
