package io.anchormind.backend.domain.entities.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor // Necesario para SuperBuilder
@AllArgsConstructor // Necesario para SuperBuilder
@SuperBuilder // Cambiar aquí
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default // Para que el Builder respete el valor por defecto
    private boolean active = true;
}