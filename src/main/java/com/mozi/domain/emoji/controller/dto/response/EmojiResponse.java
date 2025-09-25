package com.mozi.domain.emoji.controller.dto.response;

import com.mozi.domain.emoji.entity.Emoji;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmojiResponse {

    @Schema(description = "이모지 번호", example = "1")
    private final Long emojiId;

    @Schema(description = "이모지 이름", example = "웃음")
    private final String name;

    @Builder
    private EmojiResponse(Long emojiId, String name) {
        this.emojiId = emojiId;
        this.name = name;
    }

    public static EmojiResponse from(Emoji emoji) {
        return EmojiResponse.builder()
            .emojiId(emoji.getId())
            .name(emoji.getName())
            .build();
    }
}
