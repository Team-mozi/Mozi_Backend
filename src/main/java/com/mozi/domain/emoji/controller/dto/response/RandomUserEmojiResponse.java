package com.mozi.domain.emoji.controller.dto.response;

import com.mozi.domain.emoji.entity.UserEmoji;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RandomUserEmojiResponse {

    @Schema(description = "유저 이모지 번호", example = "1")
    private final Long userEmojiId;

    @Schema(description = "유저 닉네임", example = "모지")
    private final String nickname;

    @Schema(description = "이모지 번호", example = "1")
    private final Long emojiId;

    @Builder
    private RandomUserEmojiResponse(Long userEmojiId, String nickname, Long emojiId) {
        this.userEmojiId = userEmojiId;
        this.nickname = nickname;
        this.emojiId = emojiId;
    }

    public static RandomUserEmojiResponse from(UserEmoji userEmoji, String nickname) {
        return RandomUserEmojiResponse.builder()
            .userEmojiId(userEmoji.getEmojiId())
            .nickname(nickname)
            .emojiId(userEmoji.getEmojiId())
            .build();
    }
}
