    package io.anchormind.backend.domain.entities;
    
    import io.anchormind.backend.domain.entities.base.BaseEntity;
    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.SuperBuilder;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Set;

    @Entity
    @Table(name = "clinics")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder // Cambiar aquí también
    public class Clinic extends BaseEntity {
    
        @Column(nullable = false)
        private String name;
    
        private String address;
    
        private String phone;
    
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