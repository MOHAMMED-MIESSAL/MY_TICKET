package com.projets.my_ticket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Applique CORS à toutes les routes
                .allowedOrigins("http://localhost:4200")  // Autoriser Angular (frontend)
                .allowedMethods("GET", "POST", "PUT","DELETE", "OPTIONS")  // Méthodes autorisées
                .allowedHeaders("*")  // Autoriser tous les en-têtes
                .allowCredentials(true);  // Permet l'utilisation des cookies ou headers personnalisés
    }
}

