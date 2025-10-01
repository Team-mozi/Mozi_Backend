package com.mozi.domain.user.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NicknameExistsResponse {
    private final boolean exists;

    @Builder
    private NicknameExistsResponse(boolean exists) {
        this.exists = exists;
    }

    public static NicknameExistsResponse of(boolean exists) {
        return NicknameExistsResponse.builder()
                .exists(exists)
                .build();
    }
}