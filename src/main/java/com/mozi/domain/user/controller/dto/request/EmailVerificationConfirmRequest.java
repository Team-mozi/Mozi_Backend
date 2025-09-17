package com.mozi.domain.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailVerificationConfirmRequest {

    @Schema(description = "인증을 진행한 이메일", example = "test@example.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "이메일로 발송된 인증 코드", example = "123456")
    @NotBlank
    private String verificationCode;
}