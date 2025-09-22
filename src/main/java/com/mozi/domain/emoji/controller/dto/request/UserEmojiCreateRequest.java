package com.mozi.domain.emoji.controller.dto.request;

import com.mozi.domain.emoji.entity.UserEmoji;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserEmojiCreateRequest {

    @NotNull
    @Schema(description = "선택한 이모지 번호", example = "1")
    private Long emojiId;

    @Schema(description = "내용", example = "공부하는중!")
    private String text;

    @Schema(description = "이미지 URL 목록", example = "[\"http://localhost:8080/image.jpg\"]")
    private List<String> imageUrls;

    @Builder
    private UserEmojiCreateRequest(Long emojiId, String text, List<String> imageUrls) {
        this.emojiId = emojiId;
        this.text = text;
        this.imageUrls = imageUrls;
    }

    public UserEmoji toEntity(Long userId) {
        return UserEmoji.builder()
            .userId(userId)
            .emojiId(this.emojiId)
            .text(this.text)
            .build();
    }
}
