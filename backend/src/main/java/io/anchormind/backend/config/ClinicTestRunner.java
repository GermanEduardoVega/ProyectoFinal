package io.anchormind.backend.config;

import io.anchormind.backend.model.entity.Clinic;
import io.anchormind.backend.model.entity.User;
import io.anchormind.backend.model.enums.Role;
import io.anchormind.backend.repository.ClinicRepository;
import io.anchormind.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Order(2)
public class ClinicTestRunner implements CommandLineRunner {

    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("--- FASE 6: INICIALIZACIÓN DE ESCENARIO MULTI-CLÍNICA ---");

        // 1. Aseguramos la existencia de dos clínicas distintas
        Clinic central = getOrCreateClinic("Clínica AnchorMind Central", "Av. IA 123");
        Clinic oeste = getOrCreateClinic("Consultorio Periférico Oeste", "Calle Beta 456");

        // 2. Creamos/Vinculamos usuarios para testear el aislamiento

        // testUser1 -> Clínica Central
        setupUserWithClinic("testUser1", central);

        // testUser2 -> Clínica Central (Comparten clínica para probar el GET por ClinicId)
        setupUserWithClinic("testUser2", central);

        // testUser3 -> Clínica Oeste (Aislado para probar que no aparece en ClinicId 1)
        setupUserWithClinic("testUser3", oeste);

        System.out.println("--- END OF PHASE 6 SETUP ---");
    }
    //metodo privado que busca o crea la clínica y la vincula con el usuario testUser y testUser2
    private Clinic getOrCreateClinic(String name, String address) {
        return clinicRepository.findAll().stream()      //busca todas las clínicas
                .filter(c -> c.getName().equals(name))  //busca la clínica por nombre
                .findFirst()                            //devuelve la primera clínica encontrada
                .orElseGet(() -> clinicRepository.save( //si no la encuentra, la crea y la guarda
                        Clinic.builder().name(name).address(address).phone("+54 261 000000").build()
                ));
    }

    private void setupUserWithClinic(String username, Clinic clinic) {    //metodo privado que vincula el usuario con la clínica
        userRepository.findActiveByUsername(username).ifPresentOrElse(  //busca el usuario
                user -> {                                               // si lo encuentra
                    if (user.getClinic() == null) {                     //si no tiene clínica vinculada
                        user.setClinic(clinic);                         //vincula la clínica
                        userRepository.save(user);                      //guarda el usuario
                        System.out.println("DEBUG: '" + username + "' vinculado a " + clinic.getName());
                    }
                },                                                      // si no lo encuentra
                () -> {                                                 //crea el usuario
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setRole(Role.PATIENT);
                    newUser.setActive(true);
                    newUser.setClinic(clinic);
                    userRepository.save(newUser);
                    System.out.println("DEBUG: Nuevo usuario '" + username + "' creado en " + clinic.getName());
                }
        );
    }
}