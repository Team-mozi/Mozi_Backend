package com.mozi.domain.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterResponse {

    @Schema(description = "회원 번호", example = "1")
    private Long userId;

    @Schema(description = "이메일", example = "test@example.com")
    private String email;

    @Builder
    public RegisterResponse(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
