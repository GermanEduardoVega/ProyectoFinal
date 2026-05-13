package io.anchormind.backend.business.facade.impl;

import io.anchormind.backend.business.facade.BaseFacade;
import io.anchormind.backend.business.facade.BaseFacadeImpl;
import io.anchormind.backend.business.mapper.ClinicMapper;
import io.anchormind.backend.business.services.ClinicService;
import io.anchormind.backend.domain.dto.ClinicDTO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ClinicFacadeImpl extends BaseFacadeImpl<ClinicDTO, Long, ClinicService> implements BaseFacade<ClinicDTO, Long> {

     final private ClinicMapper clinicMapper;
    public ClinicFacadeImpl(ClinicService clinicService, ClinicMapper clinicMapper) {
        super(clinicService);   // Inyectamos el service
        this.clinicMapper = clinicMapper;
    }


    @Override
    public List<ClinicDTO> findAll() throws Exception {
        return service.findAllActiveDTO();
    }

    @Override
    public ClinicDTO save(ClinicDTO clinicDTO) throws Exception {
        // Aquí coordinas la lógica: primero service, luego quizás un log o IA
        // Aquí podés agregar lógica extra (ej: auditoría con IA)
        return service.createClinic(clinicDTO);
    }


    public ClinicDTO finById(Long id) throws Exception {
        return service.findActiveByIdDTO(id);
    }

    public ClinicDTO findByName(String name) throws Exception {
        return service.findActiveByNameDTO(name);
    }

    public ClinicDTO updateInstitutionalData(Long id, ClinicDTO dto) throws Exception {
        return service.updateInstitutionalData(id, dto);
    }

    @Override // Asegúrate de que BaseFacade declare este método
    public void delete(Long id) throws Exception {
        // Aquí se ejecuta el borrado lógico de ClinicServiceImpl automáticamente
        service.delete(id);
    }

}
