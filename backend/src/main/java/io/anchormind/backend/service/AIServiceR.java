package io.anchormind.backend.service;


import io.anchormind.backend.dto.AIAnalysisResponse;
import io.anchormind.backend.dto.AnxietyRecordDTO;
import io.anchormind.backend.dto.GeminiResponse;
import io.anchormind.backend.dto.UserDTO;
import io.anchormind.backend.model.entity.AnxietyRecord;
import io.anchormind.backend.model.entity.User;
import io.anchormind.backend.repository.AnxietyRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AIServiceR {
    @Autowired
    private AnxietyRecordRepository anxietyRepository;

    @Autowired
    private UserService userService;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    // Inyectamos el ObjectMapper que ya trae Spring Boot por defecto
    public AIServiceR(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    // Constantes para evitar "Magic Strings"
    private static final String GEMINI_HOST = "generativelanguage.googleapis.com";
    private static final String GEMINI_PATH = "/v1beta/models/gemini-2.5-flash:generateContent";
    @Value("${GEMINI_API_KEY}")
    private String apiKey;
    private static final Logger log = LoggerFactory.getLogger(AIServiceR.class);


    public AIAnalysisResponse getAnalysisFromAI(String userSentimentText) {
        String prompt = buildPrompt(userSentimentText);

        var requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        try {
            String rawResponse = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host(GEMINI_HOST)
                            .path(GEMINI_PATH)
                            .queryParam("key", apiKey.trim())
                            .build())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(GeminiResponse.class)
                    .map(this::extractTextFromResponse)
                    .block();

            return parseJsonResponse(rawResponse);

        } catch (Exception e) {
            log.error("Error al conectar con Gemini API: {}", e.getMessage());
            // Es mejor devolver un objeto vacío o lanzar una excepción personalizada
            return new AIAnalysisResponse();
        }
    }

    // Encapsulamos la lógica del Prompt para que el método principal sea más legible
    private String buildPrompt(String text) {
        String cleanText = text.replace("\"", "\\\"").replace("\n", " ");
        return String.format(
                "Analiza el siguiente texto de un paciente con ansiedad: '%s'. " +
                        "Responde estrictamente un JSON: " +
                        "{\"anxietyLevel\": número del 1 al 10, \"triggerIdentified\": \"texto\", \"technique\": \"texto\", \"applicability\": \"texto\", \"awarenessMessage\": \"texto\", \"actionSteps\": [\"texto\", \"texto\", \"texto\"]}",
                cleanText
        );
    }

    private String extractTextFromResponse(GeminiResponse response) {
        if (response != null && !response.getCandidates().isEmpty()) {
            return response.getCandidates().get(0).getContent().getParts().get(0).getText();
        }
        return "{}";
    }

    private AIAnalysisResponse parseJsonResponse(String rawResponse) {
        try {
            String jsonClean = rawResponse.replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();
            log.info("JSON LIMPIO QUE LLEGA DE IA: " + jsonClean);
            return objectMapper.readValue(jsonClean, AIAnalysisResponse.class);
        } catch (Exception e) {
            log.error("Error al parsear el JSON de la IA: {}", e.getMessage());
            return new AIAnalysisResponse();
        }
    }

    @Transactional
    public AnxietyRecordDTO analyzeAndSaveForUser(String userText, String username) {
        //A.Buscar el usuario(Metadatos) en la base de datos

        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado o inactivo: " + username));

        // B. LLAMAR A LA IA (PASANDOLE EL TEXTO LIBRE DEL USUARIO)
        AIAnalysisResponse aiAnalysis = getAnalysisFromAI(userText);

        // C. PREPARAR EL REGISTRO PARA GUARDARLO EN LA BASE DE DATOS
        AnxietyRecord record = new AnxietyRecord();
        record.setUser(user);                       //VINCULACION CON EL USUARIO
        record.setRawInput(userText);
        record.setTimeStamp(LocalDateTime.now());

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

        // D. GUARDAR EL REGISTRO (Guardamos la entidad en la DB)
        AnxietyRecord savedRecord = anxietyRepository.save(record);

        // E. TRADUCCIÓN (Convertimos la entidad guardada en el DTO que prometimos)
        return mapToDTO(savedRecord);
    }

    private AnxietyRecordDTO mapToDTO(AnxietyRecord entity) {
        // 1. Extraemos el nombre de la clínica navegando por el grafo de objetos
        // Esto funciona porque estamos dentro de la transacción del Service
        String clinicName = (entity.getUser().getClinic() != null)
                ? entity.getUser().getClinic().getName()
                : "Sin Clínica Asignada";

        // 2. Creamos el UserDTO (el "paquete" de datos del usuario)
        UserDTO userDTO = new UserDTO(
                entity.getUser().getId(),
                entity.getUser().getUsername(),
                entity.getUser().getRole().toString(),
                clinicName
        );

        // 3. Creamos y devolvemos el DTO principal
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
