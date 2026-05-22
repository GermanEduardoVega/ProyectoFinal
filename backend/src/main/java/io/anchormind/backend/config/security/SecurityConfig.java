package io.anchormind.backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Definimos el BcryptPasswordEncoder como un Bean para poder inyectarlo en el UserService
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Por ahora, le decimos a Spring Security que permita TODO para que no nos rompa las pruebas en Insomnia
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivamos CSRF porque vamos a usar JWT/Stateless
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permitimos todo temporalmente para la Etapa A
                );

        return http.build();
    }
}