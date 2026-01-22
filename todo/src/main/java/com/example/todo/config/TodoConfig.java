package com.example.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class TodoConfig {
    
    @Bean
    OpenAPI openAPI(){
        return new OpenAPI()
        .components(null)
        .info(new Info()
            .title("Todo API")
            .description("Todo API 명세서")
            .version("V1.0.0"));
    }
}
