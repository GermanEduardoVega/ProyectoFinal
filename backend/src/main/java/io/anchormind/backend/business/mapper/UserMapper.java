package io.anchormind.backend.business.mapper;

import io.anchormind.backend.domain.dto.UserCreateDTO;
import io.anchormind.backend.domain.dto.UserDTO;
import io.anchormind.backend.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {


    public UserDTO toDTO(User user) {
        if (user == null) return null;

        String clinicName = (user.getClinic() != null) ? user.getClinic().getName() : "Sin Clínica";

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().name(),
                clinicName,
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

        // El vínculo con la clínica se hará en el Service antes de guardar
        return user;
    }
}