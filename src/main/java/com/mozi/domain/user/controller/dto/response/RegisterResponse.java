package com.mozi.domain.user.controller.dto.response;

import com.mozi.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterResponse {

    @Schema(description = "회원 번호", example = "1")
    private final Long userId;

    @Builder
    private RegisterResponse(Long userId) {
        this.userId = userId;
    }

    public static RegisterResponse from(User user) {
        return RegisterResponse.builder()
                .userId(user.getId())
                .build();
    }
}
