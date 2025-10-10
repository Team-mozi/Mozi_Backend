package com.mozi.global.config.swagger;

import com.mozi.global.response.ApiResponse;
import com.mozi.global.response.ErrorCode;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                "/api/emojis/highlights",
                "/api/users/password-reset/",
                "/api/user-emojis/"
            );

            openApi.getPaths().forEach((path, pathItem) -> {
                boolean excluded = excludedPaths.stream().anyMatch(path::startsWith);

                if (!excluded) {
                    pathItem.readOperations().forEach(operation -> {
                        operation.addParametersItem(authHeader);
                    });
                } else {
                    if (path.matches("/api/user-emojis/.+/comments")) {
                        Operation getOperation = pathItem.getGet();
                        if (getOperation != null) {
                            getOperation.setSecurity(new ArrayList<>());
                        }
                    } else {
                        pathItem.readOperations().forEach(operation -> {
                            operation.setSecurity(new ArrayList<>());
                        });
                    }
                }
            });
        };
    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            ApiErrorCodeExamples apiErrorCodeExamples = handlerMethod.getMethodAnnotation(ApiErrorCodeExamples.class);

            if (apiErrorCodeExamples != null) {
                generateErrorCodeResponseExample(operation, apiErrorCodeExamples.value());
            }
            return operation;
        };
    }

    private void generateErrorCodeResponseExample(Operation operation, ErrorCode[] errorCodes) {
        ApiResponses responses = operation.getResponses();

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders;
        statusWithExampleHolders = Arrays.stream(errorCodes)
            .map(
                errorCode -> ExampleHolder.builder()
                    .holder(getSwaggerExample(errorCode))
                    .code(errorCode.getStatus().value())
                    .name(errorCode.name())
                    .build()
            )
            .collect(Collectors.groupingBy(ExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private Example getSwaggerExample(ErrorCode errorCode) {
        Example example = new Example();
        example.setSummary(errorCode.getMessage());
        example.setValue(ApiResponse.error(errorCode));
        return example;
    }

    private void addExamplesToResponses(ApiResponses responses,
                                        Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach(
            (status, v) -> {
                Content content = new Content();
                MediaType mediaType = new MediaType();
                io.swagger.v3.oas.models.responses.ApiResponse apiResponse =
                    new io.swagger.v3.oas.models.responses.ApiResponse();

            v.forEach(
                exampleHolder -> mediaType.addExamples(
                    exampleHolder.getName(),
                    exampleHolder.getHolder()
                )
            );

            content.addMediaType("application/json", mediaType);
            apiResponse.setContent(content);
            responses.addApiResponse(String.valueOf(status), apiResponse);
        });
    }
}
