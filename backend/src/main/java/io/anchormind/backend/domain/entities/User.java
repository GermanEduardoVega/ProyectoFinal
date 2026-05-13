package io.anchormind.backend.domain.entities;

import io.anchormind.backend.domain.entities.base.BaseEntity;
import io.anchormind.backend.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;



    @Enumerated(EnumType.STRING)
    //sugiere que el campo se almacene como un String es decir un VARCHAR y no como un INTEGER
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_subscribed_to_newsletter")
    private Boolean isSubscribedToNewsletter = false;

    @ManyToOne(fetch = FetchType.LAZY)
    //No queremos que cada vez que busques un usuario, Hibernate traiga todos los datos de la clínica de forma obligatoria. Solo los traerá si haces user.getClinic(). Esto optimiza la memoria de tu backend.
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}