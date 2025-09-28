package com.mozi.domain.emoji.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UserEmojiHighlightsResponse {

    @Schema(description = "랜덤 이모지 목록", example = "[{\"userEmojiId\": 1, \"emojiId\": \"1\"}]")
    private List<RandomUserEmojiResponse> randomEmojis;

    @Schema(description = "대표 이모지 목록", example = "[{\"emojiId\": 1}]")
    private List<RepresentativeEmojiResponse> representativeEmojis;

    @Schema(description = "내 최신 이모지", example = "{\"userEmojiId\": 1, \"userId\": \"1\" \"emojiId\": \"1\"}")
    private LatestMyEmojiResponse latestMyEmojiResponse;
}
