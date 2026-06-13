package com.basics.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Library API")
                .description("API REST de gestion de livres — projet Spring Boot basics")
                .version("0.0.1"));
    }
}
