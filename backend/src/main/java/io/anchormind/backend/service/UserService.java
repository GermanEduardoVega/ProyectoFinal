package io.anchormind.backend.service;

import io.anchormind.backend.dto.UserCreateDTO;
import io.anchormind.backend.dto.UserDTO;
import io.anchormind.backend.mapper.UserMapper;
import io.anchormind.backend.model.entity.User;
import io.anchormind.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public Optional<User> getUserByUsername(String username){
        return userRepository.findActiveByUsername(username);

    }

    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username) {
        return userRepository.findActiveByUsername(username)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("El usuario '" + username + "' no existe o está inactivo."));
    }

    @Transactional
    public UserDTO create(UserCreateDTO dto){
    // 1. Validar si el username ya está en uso
    if (userRepository.findActiveByUsername(dto.username()).isPresent()) {
        throw new IllegalArgumentException("El nombre de usuario '" + dto.username() + "' ya existe.");
    }

    // 2. Validar si el email ya está en uso (Agregá este método a tu UserRepository si no lo tenés)
    if (userRepository.existsByEmail(dto.email())) {
        throw new IllegalArgumentException("El email '" + dto.email() + "' ya está registrado.");
    }

    // 3. Si todo está bien, procedemos
        User user = userMapper.toEntity(dto);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }


}
