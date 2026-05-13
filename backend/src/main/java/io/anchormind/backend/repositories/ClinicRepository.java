package io.anchormind.backend.repositories;

import io.anchormind.backend.domain.entities.Clinic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicRepository extends BaseRepository<Clinic, Long> {
    List<Clinic> findAllByActiveTrue(); // Busca todas las clínicas activas

    List<Clinic> findAllByActiveFalse(); // Busca todas las clínicas inactivas

    // Búsqueda específica por nombre (ignora mayúsculas/minúsculas)
    Optional<Clinic> findByNameIgnoreCaseAndActiveTrue(String name);

}
