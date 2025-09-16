package com.mozi.domain.emoji.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserEmojiCreateRequest {

    @NotBlank
    @Schema(description = "이모지 번호", example = "1")
    private Long emojiId;

    @Schema(description = "내용", example = "공부를 했다.")
    private String text;
}
