package io.anchormind.backend.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class AnxietyRecordRequestDTO {
    @NotBlank(message = "El texto de entrada no puede estar vacio")
    private String rawInput;

    // Constructor vacío (necesario para que Jackson pueda instanciarlo)
    public AnxietyRecordRequestDTO() {
    }

    // Constructor con parámetros
    public AnxietyRecordRequestDTO(String rawInput) {
        this.rawInput = rawInput;
    }

    //Getters(Spring lo necesita para leer el dato)
    //Setters(Jackson lo necesita para inyectar el dato desde el JSON)
    public String getRawInput() {
        return rawInput;
    }
    public void setRawInput(String rawInput) {
        this.rawInput = rawInput;
    }
}
