package com.md.scheduler.configuration.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class SwaggerConfig {

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(
                        "bearer-key",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }

    @Bean
    GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group("API v1")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    @Profile("!prod")
    GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder()
                .group("Actuator")
                .pathsToMatch("/actuator/**")
                .pathsToExclude("/actuator/health/*")
                .build();
    }
}
