package io.anchormind.backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Vincula el bean corsConfigurationSource definido abajo a la cadena de filtros
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Desactivamos CSRF ya que manejamos JWT
                .authorizeHttpRequests(auth -> auth
                        // Por ahora permitimos crear usuarios, o endpoints públicos que necesites
                        .requestMatchers("/api/v1/users/create").permitAll()
                        // Cualquier otra petición (como registrar ansiedad) REQUIERE token
                        .anyRequest().authenticated()
                )
                // 🛡️ Aquí le decimos a Spring que actúe como Resource Server y valide JWTs
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter( jwtAuthenticationConverter() )));

        return http.build();
    }

    // 2. Bean de configuración de orígenes y cabeceras permitidas
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Lee los orígenes permitidos desde variable de entorno o usa localhost por defecto
        String allowedOrigins = System.getenv("CORS_ALLOWED_ORIGINS");
        if (allowedOrigins != null && !allowedOrigins.isBlank()) {
            configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        } else {
            configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        }

        // Permite el origen del servidor de desarrollo de Vite
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        // Permite los métodos necesarios para la API
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite la cabecera Authorization imprescindible para el Bearer JWT
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    // 3.Convertidor para transformar los claims de Auth0 en Roles entendibles por Spring ("ROLE_PATIENT", etc.)
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Por defecto Auth0 suele meter los scopes/roles, esto asegura que Spring los lea con el prefijo correcto
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        // Reemplazar "permissions" por el claim exacto donde Auth0 colocó el array de roles:
        grantedAuthoritiesConverter.setAuthoritiesClaimName("https://my-app.example.com/roles");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}