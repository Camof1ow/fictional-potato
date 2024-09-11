package com.example.japanesenamegenerator.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("1.0.0") // 버전
                .title("Diner API")
                .description("Diner API documentation");
        return new OpenAPI().info(info);
    }

}