package io.anchormind.backend.business.facade.base;

import io.anchormind.backend.domain.dto.ClinicDTO;

import java.util.List;

public interface BaseFacade<DTO,ID> {
    List<DTO> findAll() throws Exception;
    DTO findById(ID id) throws Exception;
    DTO save(DTO dto) throws Exception;
    void delete(ID id) throws Exception;
}
