package io.anchormind.backend.service;



import io.anchormind.backend.dto.AIAnalysisResponse;
import io.anchormind.backend.dto.GeminiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.Map;

@Service
public class AIServiceR {

    private static final Logger log = LoggerFactory.getLogger(AIServiceR.class);

    // Constantes para evitar "Magic Strings"
    private static final String GEMINI_HOST = "generativelanguage.googleapis.com";
    private static final String GEMINI_PATH = "/v1beta/models/gemini-2.5-flash:generateContent";

    @Value("${GEMINI_API_KEY}")
    private String apiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    // Inyectamos el ObjectMapper que ya trae Spring Boot por defecto
    public AIServiceR(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public AIAnalysisResponse getAnalysisFromAI(String userText) {
        String prompt = buildPrompt(userText);

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
                        "{\"anxietyLevel\": número del 1 al 10, \"triggerIdentified\": \"texto\", \"technique\": \"texto\", \"applicability\": \"texto\"}",
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
            System.out.println("JSON LIMPIO QUE LLEGA DE IA: " + jsonClean);
            return objectMapper.readValue(jsonClean, AIAnalysisResponse.class);
        } catch (Exception e) {
            log.error("Error al parsear el JSON de la IA: {}", e.getMessage());
            return new AIAnalysisResponse();
        }
    }
}
