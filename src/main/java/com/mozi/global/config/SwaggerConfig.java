package com.mozi.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Mozi")
                .version("1.0")
                .description("Mozi API Description")
            )
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                    );
    }

    @Bean
    public GlobalOpenApiCustomizer authorizationHeaderCustomizer() {
        return openApi -> {
            Parameter authHeader = new Parameter()
                .name("Authorization")
                .description("JWT Access Token")
                .in("header")
                .required(false)
                .schema(new StringSchema().example("Bearer <your_token>"));

            List<String> excludedPaths = List.of(
                "/api/users/register",
                "/api/users/login",
                "/api/users/email-verifications/",
                "/api/users/reissue",
                "/api/emojis/highlights"
            );

            openApi.getPaths().forEach((path, pathItem) -> {
                boolean excluded = excludedPaths.stream().anyMatch(path::startsWith);

                if (!excluded) {
                    pathItem.readOperations().forEach(operation ->
                        operation.addParametersItem(authHeader)
                    );
                }
            });
        };
    }
}
