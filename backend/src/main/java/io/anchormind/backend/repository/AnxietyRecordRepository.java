package io.anchormind.backend.repository;

import io.anchormind.backend.model.entity.AnxietyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnxietyRecordRepository extends JpaRepository<AnxietyRecord, Long> {

}
