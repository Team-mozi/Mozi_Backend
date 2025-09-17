package com.mozi.domain.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequest {

    @Schema(description = "현재 계정의 비밀번호", example = "1234")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
