package com.aroma.borealis.tienda_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a TODAS las rutas
                .allowedOriginPatterns("*") // Deja pasar a CUALQUIERA
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Todos los verbos
                .allowedHeaders("*") // Todos los headers
                .allowCredentials(true); // Permite credenciales
    }
}