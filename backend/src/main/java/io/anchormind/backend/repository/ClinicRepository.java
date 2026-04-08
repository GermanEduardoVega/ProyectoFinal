package io.anchormind.backend.repository;

import io.anchormind.backend.model.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findAllByActiveTrue(); // Busca todas las clínicas activas

    List<Clinic> findAllByActiveFalse(); // Busca todas las clínicas inactivas

}
