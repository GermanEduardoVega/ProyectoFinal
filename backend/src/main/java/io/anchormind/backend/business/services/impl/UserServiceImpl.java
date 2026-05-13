package io.anchormind.backend.business.services.impl;

import io.anchormind.backend.business.services.UserService;
import io.anchormind.backend.business.services.base.BaseServiceImpl;
import io.anchormind.backend.domain.dto.UserCreateDTO;
import io.anchormind.backend.domain.dto.UserDTO;
import io.anchormind.backend.business.mapper.UserMapper;
import io.anchormind.backend.domain.entities.User;
import io.anchormind.backend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //El constructor manual de inyección de dependencias
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

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

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        user.setActive(false); // Auditoría: Borrado Lógico
        userRepository.save(user);
        return true;
    }


}
