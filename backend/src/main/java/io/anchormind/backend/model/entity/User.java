package io.anchormind.backend.model.entity;

import io.anchormind.backend.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)    //sugiere que el campo se almacene como un String es decir un VARCHAR y no como un INTEGER
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_subscribed_to_newsletter")
    private Boolean isSubscribedToNewsletter = false;

    // Regla de desarrollo: Baja Lógica
    @Column(nullable = false)
    private Boolean active = true;
}