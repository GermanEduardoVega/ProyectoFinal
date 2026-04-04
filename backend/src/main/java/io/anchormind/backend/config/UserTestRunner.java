package io.anchormind.backend.config;

import io.anchormind.backend.model.entity.User;
import io.anchormind.backend.model.enums.Role;
import io.anchormind.backend.repository.UserRepository;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

//@Configuration
public class UserTestRunner {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            System.out.println("---FASE DE PRUEBAS 3: TESTEO DE LA PERSISTENCIA DE USUARIOS---");


            String testUsername = "testUser1";

            // Usamos tu método JPQL para verificar si ya existe
            Optional<User> existingUser = userRepository.findActiveByUsername(testUsername);

            if (existingUser.isEmpty()) {
                //1. Solo lo creamos si no existe
                User testUser = new User();
                testUser.setUsername(testUsername);
                testUser.setRole(Role.PATIENT);
                testUser.setActive(true);
                testUser.setIsSubscribedToNewsletter(false);

                userRepository.save(testUser);
                System.out.println("DEBUG: New test user created.");
            } else {
                System.out.println("DEBUG: User '" + testUsername + "' already exists in DB. Skipping insertion.");
            }

            // Recuperación final para confirmar que el SELECT funciona
            User finalUser = userRepository.findActiveByUsername(testUsername).get();
            System.out.println("SUCCESS: Working with user: " + finalUser.getUsername() + " [Role: " + finalUser.getRole() + "]");

            System.out.println("--- END OF TEST ---");

        };
    }
}
