package com.mozi.domain.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterRequest {

    @Schema(description = "이메일", example = "test@example.com")
    @NotBlank
    private String email;

    @Schema(description = "비밀번호", example = "1234")
    @NotBlank
    private String password;

    @Schema(description = "약관동의 여부", example = "true")
    @AssertTrue(message = "서비스 이용을 위해 약관에 동의해야 합니다.")
    private boolean agreed;

}
