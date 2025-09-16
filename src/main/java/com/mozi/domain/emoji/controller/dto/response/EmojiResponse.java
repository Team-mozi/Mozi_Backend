package com.mozi.domain.emoji.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class EmojiResponse {

    @Schema(description = "이모지 번호", example = "1")
    private Long emojiId;

    @Schema(description = "이모지 url", example = "http://localhost:8080/image.jpg")
    private String emojiUrl;
}
