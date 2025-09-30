package com.mozi.domain.emoji.controller.dto.response;

import com.mozi.domain.emoji.entity.UserEmoji;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LatestMyEmojiResponse {

    @Schema(description = "유저 이모지 번호", example = "1")
    private final Long userEmojiId;

    @Schema(description = "유저 번호", example = "1")
    private final Long userId;

    @Schema(description = "이모지 번호", example = "1")
    private final Long emojiId;

    @Builder
    private LatestMyEmojiResponse(Long userEmojiId, Long userId, Long emojiId) {
        this.userEmojiId = userEmojiId;
        this.userId = userId;
        this.emojiId = emojiId;
    }

    public static LatestMyEmojiResponse from(UserEmoji userEmoji) {
        return LatestMyEmojiResponse.builder()
            .userEmojiId(userEmoji.getId())
            .userId(userEmoji.getUserId())
            .emojiId(userEmoji.getEmojiId())
            .build();
    }
}
