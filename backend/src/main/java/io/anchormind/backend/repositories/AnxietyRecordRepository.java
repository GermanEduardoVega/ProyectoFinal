package io.anchormind.backend.repositories;

import io.anchormind.backend.domain.entities.AnxietyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnxietyRecordRepository extends BaseRepository<AnxietyRecord, Long> {
    @Query("SELECT ar FROM AnxietyRecord ar " +
            "WHERE ar.user.id = :userId " +
            "ORDER BY ar.timeStamp DESC")
    List<AnxietyRecord> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM AnxietyRecord r" +
            " WHERE r.user.clinic.id = :clinicId" +
            " ORDER BY r.timeStamp DESC")
    List<AnxietyRecord> findAllByClinicId(@Param("clinicId") Long clinicId);

}
