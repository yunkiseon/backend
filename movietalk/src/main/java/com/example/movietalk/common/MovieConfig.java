package com.example.movietalk.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class MovieConfig {
    
    @Bean
    OpenAPI openAPI(){
        return new OpenAPI()
        .components(null)
        .info(new Info()
            .title("MovieTalk API")
            .description("MovieTalk API 명세서")
            .version("V1.0.0"));
    }
}
