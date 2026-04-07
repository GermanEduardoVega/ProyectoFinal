package io.anchormind.backend.model.entity;

import io.anchormind.backend.model.entity.User;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;


@Entity
@Table(name = "anxiety_records")
@Data // Esto genera Getters, Setters, toString y Equals automáticamente
public class AnxietyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
