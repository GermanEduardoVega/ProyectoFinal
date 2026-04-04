package io.anchormind.backend.config;

import io.anchormind.backend.model.entity.Clinic;
import io.anchormind.backend.model.entity.User;
import io.anchormind.backend.repository.ClinicRepository;
import io.anchormind.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component
@RequiredArgsConstructor
@Order(2) // Se ejecuta después de la creación de usuarios base
public class ClinicTestRunner implements CommandLineRunner {

    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional // Importante para manejar la sesión de Lazy Loading
    public void run(String... args) throws Exception {
        System.out.println("--- FASE DE PRUEBAS 4: RELACIÓN CLÍNICA-USUARIO ---");

        // 1. Crear clínica si no existe
        Clinic primaryClinic = clinicRepository.findAll().stream().findFirst()
                .orElseGet(() -> {
                    Clinic newClinic = Clinic.builder()
                            .name("Clínica AnchorMind Central")
                            .address("Av. IA 123")
                            .phone("+54 261 1234567")
                            .build();
                    return clinicRepository.save(newClinic);
                });

        System.out.println("DEBUG: Clínica lista: " + primaryClinic.getName());

        // 2. Vincular al testUser1 con la clínica
        userRepository.findActiveByUsername("testUser1").ifPresent(user -> {
            if (user.getClinic() == null) {
                user.setClinic(primaryClinic);
                userRepository.save(user);
                System.out.println("SUCCESS: Usuario '" + user.getUsername() + "' vinculado a la clínica.");
            } else {
                System.out.println("DEBUG: El usuario ya pertenece a: " + user.getClinic().getName());
            }
        });

        System.out.println("--- END OF PHASE 4 ---");
    }
}