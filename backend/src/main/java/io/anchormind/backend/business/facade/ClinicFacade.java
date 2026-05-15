package io.anchormind.backend.business.facade;

import io.anchormind.backend.business.facade.base.BaseFacade;
import io.anchormind.backend.domain.dto.ClinicDTO;

public interface ClinicFacade extends BaseFacade<ClinicDTO,Long> {
    ClinicDTO findByName(String name) throws Exception;
    ClinicDTO updateInstitutionalData(Long id, ClinicDTO dto) throws Exception;
}
