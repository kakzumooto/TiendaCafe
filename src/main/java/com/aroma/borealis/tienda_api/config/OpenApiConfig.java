package com.aroma.borealis.tienda_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Tu Nombre",
                        email = "tuemail@ejemplo.com",
                        url = "https://tuportafolio.com"
                ),
                description = "Documentación de la API para el E-commerce de Café AromaBorealis",
                title = "AromaBorealis API - Full Stack Java",
                version = "1.0",
                license = @License(
                        name = "Licencia Standard",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Servidor Local",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de Producción (Render)",
                        url = "https://tu-app-en-render.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT de autenticación. Ingrese su token aquí.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}