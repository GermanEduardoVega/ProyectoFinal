package io.anchormind.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        // Podríamos configurar límites de memoria o tiempos de espera aquí más adelante
        return WebClient.builder();
    }
}