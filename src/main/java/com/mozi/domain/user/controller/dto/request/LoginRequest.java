package com.mozi.domain.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Schema(description = "이메일", example = "test@example.com")
    @NotBlank
    private String email;

    @Schema(description = "비밀번호", example = "Password123!")
    @NotBlank
    private String password;
}