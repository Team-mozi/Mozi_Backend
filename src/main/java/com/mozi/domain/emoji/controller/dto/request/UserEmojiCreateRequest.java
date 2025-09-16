package com.mozi.domain.emoji.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class UserEmojiCreateRequest {

    @NotNull
    @Schema(description = "이모지 번호", example = "1")
    private Long emojiId;

    @Schema(description = "내용", example = "공부하는중!")
    private String text;

    @Schema(description = "이미지 URL 목록", example = "http://localhost:8080/img1.jpg")
    private List<String> imageUrls;
}
