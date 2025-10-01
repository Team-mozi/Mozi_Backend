package com.mozi.domain.user.controller.dto.response;

import com.mozi.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final String refreshToken;

    @Schema(description = "액세스 토큰", example = "125687c4-80ac-41b7-898b-f50c4cf7e12c")
    private final String accessToken;

    @Schema(description = "회원 번호", example = "1")
    private final Long userId;

    @Builder
    private LoginResponse(String accessToken, Long userId, String refreshToken) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public static LoginResponse from(User user, String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }
}
