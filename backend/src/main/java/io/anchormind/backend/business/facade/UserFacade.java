package io.anchormind.backend.business.facade;

import io.anchormind.backend.business.facade.base.BaseFacade;
import io.anchormind.backend.domain.dto.UserCreateDTO;
import io.anchormind.backend.domain.dto.UserDTO;

public interface UserFacade extends BaseFacade<UserDTO,Long > {
    UserDTO create(UserCreateDTO dto);
    UserDTO findByUsername(String username);


}
