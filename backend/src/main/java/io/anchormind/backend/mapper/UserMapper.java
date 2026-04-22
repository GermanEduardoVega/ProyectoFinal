package io.anchormind.backend.mapper;

import io.anchormind.backend.dto.UserCreateDTO;
import io.anchormind.backend.dto.UserDTO;
import io.anchormind.backend.model.entity.User;
import io.anchormind.backend.model.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserDTO toDTO(User user) {
        if (user == null) return null;

        String clinic = (user.getClinic() != null) ? user.getClinic().getName() : "Sin Clínica";

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().name(),
                clinic,
                user.isActive(),
                user.getCreatedAt()
        );
    }

    public User toEntity(UserCreateDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password()); // Nota: En el futuro aquí usaremos BCrypt
        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        user.setRole(dto.role());
        user.setActive(true);
        return user;
    }
}