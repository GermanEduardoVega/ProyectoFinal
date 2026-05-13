package io.anchormind.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AIAnalysisResponse {
    @JsonProperty("anxietyLevel")
    private Integer anxietyLevel;
    @JsonProperty("triggerIdentified")
    private String triggerIdentified;
    @JsonProperty("technique")
    private String technique;
    @JsonProperty("applicability")
    private String applicability;
    @JsonProperty("awarenessMessage")
    private String awarenessMessage;
    @JsonProperty("actionSteps")
    private String[] actionSteps;




}