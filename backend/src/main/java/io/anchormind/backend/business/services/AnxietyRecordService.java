package io.anchormind.backend.business.services;

import io.anchormind.backend.business.services.base.BaseService;
import io.anchormind.backend.domain.dto.AnxietyRecordDTO;
import io.anchormind.backend.domain.entities.AnxietyRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnxietyRecordService extends BaseService<AnxietyRecord,Long> {
    @Transactional
    AnxietyRecordDTO analyzeAndSave(String userText, String username);

    List<AnxietyRecordDTO> findAllRecords();
    List<AnxietyRecordDTO> findRecordsByPatient(String username);

    List<AnxietyRecordDTO> findRecordsByClinic(Long clinicId);
}
