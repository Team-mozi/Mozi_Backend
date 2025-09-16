package com.mozi.domain.emoji.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RandomUserEmojiResponse {

    @Schema(description = "유저 이모지 번호", example = "1")
    private Long userEmojiId;

    @Schema(description = "유저 닉네임", example = "모지")
    private String nickname;

    @Schema(description = "이모지 번호", example = "1")
    private Long emojiId;
}
