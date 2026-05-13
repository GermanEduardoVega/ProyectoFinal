package io.anchormind.backend.domain.entities;

import io.anchormind.backend.domain.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.LocalDateTime;


@Entity
@Table(name = "anxiety_records")
@Getter // Cambiamos @Data por Getter/Setter para evitar conflictos con SuperBuilder
@Setter
@NoArgsConstructor // Genera el constructor vacío que el Mapper y JPA necesitan
@AllArgsConstructor // Genera el constructor con todos los campos que SuperBuilder requiere
@SuperBuilder // ÚNICAMENTE SuperBuilder (sin @Builder)
@EqualsAndHashCode(callSuper = true)
public class AnxietyRecord extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String rawInput; // El texto libre o tokens que manda el Front
    private Integer anxietyLevel; // Del 1 al 10 (inferido por la IA)
    private String triggerIdentified; // Gatillo (inferido por la IA)
    @Column(name = "technique", length = 1000)
    private String technique; // Técnica (inferido por la IA)
    @Column(name = "aplicability", length = 1000)
    private String applicability; // Aplicabilidad (inferido por la IA)
    @Column(name = "awareness_message", columnDefinition = "TEXT")
    private String awarenessMessage;
    // Para el array action_steps (TEXT[])
    @Column(name = "action_steps", columnDefinition = "text[]")
    private String[] actionSteps;

    /*** Aquí guardaremos la respuesta de la IA como un String JSON por ahora***/
    @Column(name = "ai_response_json", columnDefinition = "TEXT")
    private String aiResponseJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        this.timeStamp = LocalDateTime.now();
    }


}





