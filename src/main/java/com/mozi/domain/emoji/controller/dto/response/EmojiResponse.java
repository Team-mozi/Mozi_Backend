package com.mozi.domain.emoji.controller.dto.response;

import com.mozi.domain.emoji.entity.Emoji;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmojiResponse {

    @Schema(description = "이모지 번호", example = "1")
    private final Long emojiId;

    @Builder
    private EmojiResponse(Long emojiId) {
        this.emojiId = emojiId;
    }

    public static EmojiResponse from(Emoji emoji) {
        return EmojiResponse.builder()
            .emojiId(emoji.getId())
            .build();
    }
}
