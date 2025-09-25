package com.mozi.domain.emoji.controller.dto.response;

import com.mozi.domain.emoji.entity.Emoji;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RepresentativeEmojiResponse {

    @Schema(description = "이모지 번호", example = "1")
    private final Long emojiId;

    @Builder
    private RepresentativeEmojiResponse(Long emojiId) {
        this.emojiId = emojiId;
    }

    public static RepresentativeEmojiResponse from(Emoji emoji) {
        return RepresentativeEmojiResponse.builder()
            .emojiId(emoji.getId())
            .build();
    }
}
