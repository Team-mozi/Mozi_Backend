package com.mozi.domain.emoji.controller.dto.response;

import com.mozi.domain.emoji.entity.UserEmoji;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserEmojiDetailResponse {

    @Schema(description = "유저 이모지 번호", example = "1")
    private Long userEmojiId;

    @Schema(description = "유저 닉네임", example = "모지")
    private String nickname;

    @Schema(description = "이모지 번호", example = "1")
    private Long emojiId;

    @Schema(description = "내용", example = "공부하는중!")
    private String text;

    @Schema(description = "이미지 URL 목록", example = "http://localhost:8080/image.jpg")
    private List<String> imageUrls;

    @Schema(description = "생성 시각", example = "2025-10-21T23:59:59")
    private LocalDateTime createdAt;

    @Builder
    private UserEmojiDetailResponse(Long userEmojiId, String nickname, Long emojiId, String text, List<String> imageUrls, LocalDateTime createdAt) {
        this.userEmojiId = userEmojiId;
        this.nickname = nickname;
        this.emojiId = emojiId;
        this.text = text;
        this.imageUrls = imageUrls;
        this.createdAt = createdAt;
    }

    public static UserEmojiDetailResponse from(UserEmoji userEmoji, String nickname, List<String> imageUrls) {
        return UserEmojiDetailResponse.builder()
            .userEmojiId(userEmoji.getId())
            .nickname(nickname)
            .emojiId(userEmoji.getEmojiId())
            .text(userEmoji.getText())
            .imageUrls(imageUrls)
            .createdAt(userEmoji.getCreatedAt())
            .build();
    }
}
