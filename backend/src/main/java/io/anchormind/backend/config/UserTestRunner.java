package io.anchormind.backend.config;

import io.anchormind.backend.model.entity.User;
import io.anchormind.backend.model.enums.Role;
import io.anchormind.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1) // Corre PRIMERO para asegurar que los usuarios existan
public class UserTestRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- FASE 6: INICIALIZACIÓN DE USUARIOS BASE ---");

        // Creamos los 3 usuarios de prueba si no existen
        createBaseUser("testUser1", Role.PATIENT);
        createBaseUser("testUser2", Role.PATIENT);
        createBaseUser("testUser3", Role.PATIENT);

        System.out.println("DEBUG: Usuarios base verificados/creados.");
    }

    private void createBaseUser(String username, Role role) {
        userRepository.findActiveByUsername(username).ifPresentOrElse(
                user -> System.out.println("DEBUG: Usuario '" + username + "' ya existe."),
                () -> {
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setRole(role);
                    newUser.setActive(true);
                    userRepository.save(newUser);
                    System.out.println("SUCCESS: Creado usuario base '" + username + "'");
                }
        );
    }
}