package io.anchormind.backend.business.facade.impl;

import io.anchormind.backend.business.facade.UserFacade;
import io.anchormind.backend.business.facade.base.BaseFacadeImpl;
import io.anchormind.backend.business.services.UserService;
import io.anchormind.backend.domain.dto.ClinicDTO;
import io.anchormind.backend.domain.dto.UserCreateDTO;
import io.anchormind.backend.domain.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserFacadeImpl extends BaseFacadeImpl<UserDTO,Long, UserService> implements UserFacade {

    public UserFacadeImpl(UserService service) {
        super(service);
    }
    @Override
    public UserDTO create(UserCreateDTO dto) {
        // Aquí podrías coordinar: llamar a la IA para validar el perfil, etc.
        return service.create(dto);
    }

    @Override
    public UserDTO findByUsername(String username) {
        return service.findByUsername(username);
    }




}
