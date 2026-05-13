package io.anchormind.backend.business.services;

import io.anchormind.backend.business.services.base.BaseService;
import io.anchormind.backend.domain.dto.UserCreateDTO;
import io.anchormind.backend.domain.dto.UserDTO;
import io.anchormind.backend.domain.entities.User;

import java.util.Optional;

public interface UserService extends BaseService<User, Long> {
    Optional<User> getUserByUsername(String username);
    UserDTO findByUsername(String username);
    UserDTO create(UserCreateDTO dto);
}
