package com.mozi.domain.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordResetRequest {
    @Schema(description = "인증 완료된 이메일", example = "test@example.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "이메일로 발송된 인증 코드", example = "123456")
    @NotBlank
    private String verificationCode;

    @Schema(description = "새로운 비밀번호", example = "newPassword1!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$",
            message = "비밀번호는 숫자, 영문, 특수문자를 혼합하여 8자 이상이어야 합니다.")
    @NotBlank
    private String newPassword;
}
