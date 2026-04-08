package io.anchormind.backend.repository;

import io.anchormind.backend.model.entity.AnxietyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnxietyRecordRepository extends JpaRepository<AnxietyRecord, Long> {// 1. Consulta para el Paciente: Ver sus propios registros
    @Query("SELECT r FROM AnxietyRecord r WHERE r.user.username = :username ORDER BY r.timeStamp DESC")
    List<AnxietyRecord> findAllByUsername(@Param("username") String username);

    // 2. Consulta para el Profesional: Ver registros de su clínica
    @Query("SELECT r FROM AnxietyRecord r WHERE r.user.clinic.id = :clinicId ORDER BY r.timeStamp DESC")
    List<AnxietyRecord> findAllByClinicId(@Param("clinicId") Long clinicId);

}
