package com.movieapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie API")
                        .version("1.0.0")
                        .description("API for managing movies, actors, and genres")
                        .contact(new Contact()
                                .name("Movie API Support")
                                .email("support@movieapi.com")
                                .url("https://movieapi.com")));
    }
}