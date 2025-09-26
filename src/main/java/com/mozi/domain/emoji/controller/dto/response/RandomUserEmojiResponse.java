package com.mozi.domain.emoji.controller.dto.response;

import com.mozi.domain.emoji.entity.UserEmoji;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RandomUserEmojiResponse {

    @Schema(description = "유저 이모지 번호", example = "1")
    private final Long userEmojiId;

    @Schema(description = "이모지 번호", example = "1")
    private final Long emojiId;

    @Builder
    private RandomUserEmojiResponse(Long userEmojiId, Long emojiId) {
        this.userEmojiId = userEmojiId;
        this.emojiId = emojiId;
    }

    public static RandomUserEmojiResponse from(UserEmoji userEmoji) {
        return RandomUserEmojiResponse.builder()
            .userEmojiId(userEmoji.getEmojiId())
            .emojiId(userEmoji.getEmojiId())
            .build();
    }
}
