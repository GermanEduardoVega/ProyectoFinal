package io.anchormind.backend.business.facade;

import io.anchormind.backend.business.facade.base.BaseFacade;
import io.anchormind.backend.domain.dto.AnxietyRecordDTO;

import java.util.List;

public interface AnxietyRecordFacade extends BaseFacade<AnxietyRecordDTO,Long > {
    AnxietyRecordDTO analyzeAndSave(String userText, String username);
    List<AnxietyRecordDTO> findRecordsByPatient(String username);
    List<AnxietyRecordDTO> findRecordsByClinic(Long clinicId);
    List<AnxietyRecordDTO> findAllRecords();
}
