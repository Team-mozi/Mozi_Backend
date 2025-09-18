package com.mozi.domain.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SocialLoginRequest {

    @Schema(description = "소셜 제공자", example = "kakao")
    @NotBlank
    private String provider;

    @Schema(description = "소셜 인증 토큰", example = "abc12.A0...")
    @NotBlank
    private String providerToken;

}
