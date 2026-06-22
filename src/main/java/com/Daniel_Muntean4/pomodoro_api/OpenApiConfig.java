package com.Daniel_Muntean4.pomodoro_api;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig
{
    @Bean
    public OpenAPI pomodoroApiInfo() {
        return new OpenAPI().info(new Info()
                .title("Pomodoro API")
                .version("1.0")
                .description("CRUD API for Pomodoro study sessions and topics"))
                .addSecurityItem(new SecurityRequirement().addList("bearer-auth"))
                .components(new Components().addSecuritySchemes("bearer-auth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP).scheme("bearer")
                                .bearerFormat("JWT")));


    }

}
