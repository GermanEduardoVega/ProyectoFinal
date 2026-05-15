package io.anchormind.backend.business.facade.base;

import io.anchormind.backend.business.facade.base.BaseFacade;
import io.anchormind.backend.business.services.base.BaseService;

import java.io.Serializable;
import java.util.List;

public abstract class BaseFacadeImpl<DTO, ID extends Serializable,S extends BaseService<?, ID>> implements BaseFacade<DTO, ID> {

    protected final S service;

    // Aquí podrías inyectar un Mapper genérico si existiera,
    // pero como son manuales, cada Facade usará el suyo.

    protected BaseFacadeImpl(S service) {
        this.service = service;
    }
    @Override
    public List<DTO> findAll() throws Exception {
        return null;
    }

    @Override
    public DTO findById(ID id) throws Exception {
        return null;
    }

    @Override
    public DTO save(DTO dto) throws Exception {
        return null;
    }

    @Override
    public void delete(ID id) throws Exception {

    }
}
