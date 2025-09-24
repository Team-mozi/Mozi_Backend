package com.mozi.domain.user.controller.dto.request;

import lombok.Getter;

@Getter
public class TokenRefreshRequest {
    private String refreshToken;
}