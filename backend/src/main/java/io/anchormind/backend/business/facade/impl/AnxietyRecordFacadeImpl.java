package io.anchormind.backend.business.facade.impl;

import io.anchormind.backend.business.facade.AnxietyRecordFacade;
import io.anchormind.backend.business.facade.base.BaseFacadeImpl;
import io.anchormind.backend.business.services.AnxietyRecordService;
import io.anchormind.backend.domain.dto.AnxietyRecordDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnxietyRecordFacadeImpl extends BaseFacadeImpl<AnxietyRecordDTO,Long, AnxietyRecordService> implements AnxietyRecordFacade {
    public AnxietyRecordFacadeImpl(AnxietyRecordService anxietyRecordService) {
        super(anxietyRecordService);
    }

    @Override
    public AnxietyRecordDTO analyzeAndSave(String userText, String username) {
        return service.analyzeAndSave(userText, username);
    }

    @Override
    public List<AnxietyRecordDTO> findRecordsByPatient(String username) {
        return service.findRecordsByPatient(username);
    }

    @Override
    public List<AnxietyRecordDTO> findRecordsByClinic(Long clinicId) {
        return service.findRecordsByClinic(clinicId);
    }

    @Override
    public List<AnxietyRecordDTO> findAllRecords() {
        return service.findAllRecords();
    }
}
