package io.anchormind.backend.domain.dto;

import java.time.LocalDateTime;

public record AnxietyRecordDTO(
        Long id,
        LocalDateTime timeStamp,
        String rawInput,
        Integer anxietyLevel,
        String triggerIdentified,
        String technique,
        String applicability,
        String awarenessMessage,
        String[] actionSteps,
        UserDTO user
) {
}
