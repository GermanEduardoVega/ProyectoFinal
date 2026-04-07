package io.anchormind.backend.service;

import io.anchormind.backend.dto.AnxietyRecordDTO;

import java.util.List;

public interface AnxietyRecordService {
    List<AnxietyRecordDTO> findAllRecords();
}
