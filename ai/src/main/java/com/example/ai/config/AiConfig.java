package com.example.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class AiConfig {
    
    @Bean
    OpenAPI openAPI(){
        return new OpenAPI()
        .components(null)
        .info(new Info()
            .title("AI 애플리케이션 API")
            .description("Ai API 명세서")
            .version("V1.0.0"));
    }
}
