package com.mozi.domain.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterRequest {

    @Schema(description = "이메일", example = "test@example.com")
    @NotBlank
    private String email;

    @Schema(description = "비밀번호", example = "Password123!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$",
            message = "비밀번호는 숫자, 영문, 특수문자를 혼합하여 8자 이상이어야 합니다.")
    @NotBlank
    private String password;

    @Schema(description = "약관동의 여부", example = "true")
    @AssertTrue(message = "서비스 이용을 위해 약관에 동의해야 합니다.")
    private boolean agreed;

}
