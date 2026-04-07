package io.anchormind.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clinics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String phone;

    @Builder.Default
    private boolean active = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Relación: Una clínica tiene muchos usuarios (profesionales/pacientes)
    @OneToMany(mappedBy = "clinic")
    private List<User> users;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}