package com.mozi.domain.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailVerificationRequest {

    @Schema(description = "인증을 진행할 이메일", example = "test@example.com")
    @NotBlank
    @Email
    private String email;

}
