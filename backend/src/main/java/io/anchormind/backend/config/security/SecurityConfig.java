package io.anchormind.backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivamos CSRF ya que manejamos JWT
                .authorizeHttpRequests(auth -> auth
                        // Por ahora permitimos crear usuarios, o endpoints públicos que necesites
                        .requestMatchers("/api/v1/users/create").permitAll()
                        // Cualquier otra petición (como registrar ansiedad) REQUIERE token
                        .anyRequest().authenticated()
                )
                // 🛡️ Aquí le decimos a Spring que actúe como Resource Server y valide JWTs
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));

        return http.build();
    }
}