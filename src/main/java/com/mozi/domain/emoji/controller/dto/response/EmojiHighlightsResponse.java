package com.mozi.domain.emoji.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmojiHighlightsResponse {

    @Schema(description = "랜덤 이모지 목록", example = "[{\"userEmojiId\": 1, \"emojiId\": \"1\"}]")
    private final List<RandomUserEmojiResponse> randomEmojis;

    @Schema(description = "대표 이모지 목록", example = "[{\"emojiId\": 1}]")
    private final List<RepresentativeEmojiResponse> representativeEmojis;

    @Builder
    private EmojiHighlightsResponse(List<RandomUserEmojiResponse> randomEmojis, List<RepresentativeEmojiResponse> representativeEmojis) {
        this.randomEmojis = randomEmojis;
        this.representativeEmojis = representativeEmojis;
    }

    public static EmojiHighlightsResponse from(List<RandomUserEmojiResponse> randomEmojis, List<RepresentativeEmojiResponse> representativeEmojis) {
        return EmojiHighlightsResponse.builder()
            .randomEmojis(randomEmojis)
            .representativeEmojis(representativeEmojis)
            .build();
    }
}
