package io.anchormind.backend.business.services.impl;

import io.anchormind.backend.business.services.UserService;
import io.anchormind.backend.business.services.base.BaseServiceImpl;
import io.anchormind.backend.domain.dto.UserCreateDTO;
import io.anchormind.backend.domain.dto.UserDTO;
import io.anchormind.backend.business.mapper.UserMapper;
import io.anchormind.backend.domain.entities.Clinic;
import io.anchormind.backend.domain.entities.User;
import io.anchormind.backend.repositories.ClinicRepository;
import io.anchormind.backend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository; // Necesario para validar la clínica
    private final UserMapper userMapper;

    //El constructor manual de inyección de dependencias
    public UserServiceImpl(UserRepository userRepository,
                           ClinicRepository clinicRepository,
                           UserMapper userMapper) {
        super(userRepository);
        this.userRepository = userRepository;
        this.clinicRepository = clinicRepository;
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
    @Override
    @Transactional
    public UserDTO create(UserCreateDTO dto){
        // 1. Validaciones de unicidad
        if (userRepository.findActiveByUsername(dto.username()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario '" + dto.username() + "' ya existe.");
        }

        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("El email '" + dto.email() + "' ya está registrado.");
        }

        // 2. Validaciones de clínica
        Clinic clinic = clinicRepository.findById(dto.clinicId())
                .orElseThrow(() -> new IllegalArgumentException("La clínica con ID " + dto.clinicId() + " no existe."));


        // 3. Mapeo y asignación manual del vínculo todo correcto
        User user = userMapper.toEntity(dto);
        user.setClinic(clinic); // Aquí "atamos" el usuario a la clínica

        // 4. Persistencia
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
