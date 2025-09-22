package com.mozi.domain.user.controller.dto.response;

import com.mozi.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    @Schema(description = "액세스 토큰", example = "125687c4-80ac-41b7-898b-f50c4cf7e12c")
    private String accessToken;

    @Schema(description = "회원 번호", example = "1")
    private Long userId;

    @Schema(description = "닉네임", example = "멋쟁이모지")
    private String nickname;

    @Builder
    private LoginResponse(String accessToken, Long userId, String nickname) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.nickname = nickname;
    }

    public static LoginResponse from(User user, String accessToken) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
