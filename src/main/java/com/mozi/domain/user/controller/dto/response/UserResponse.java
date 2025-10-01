package com.mozi.domain.user.controller.dto.response;

import com.mozi.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    @Schema(description = "회원 번호", example = "1")
    private final Long userId;

    @Schema(description = "닉네임", example = "멋쟁이모지")
    private final String nickname;

    @Builder
    private UserResponse(Long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
