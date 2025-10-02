package com.mozi.domain.user.controller.dto.response;

import com.mozi.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    @Schema(description = "회원 번호", example = "1")
    private final Long userId;

    @Schema(description = " 회원 닉네임", example = "멋쟁이모지")
    private final String nickname;

    @Schema(description = "액세스 토큰", example = "125687c4-80ac-41b7-898b-f50c4cf7e12c")
    private final String accessToken;

    @Schema(description = "리프래시 토큰", example = "987654c4-80ac-41b7-898b-f50c4cf7e12c")
    private final String refreshToken;

    @Builder
    private LoginResponse( Long userId, String nickname, String accessToken, String refreshToken) {
        this.userId = userId;
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginResponse from(User user, String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
