package com.mozi.domain.emoji.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UserEmojiDetailResponse {

    @Schema(description = "유저 이모지 번호", example = "1")
    private Long userEmojiId;

    @Schema(description = "유저 번호", example = "1")
    private Long userId;

    @Schema(description = "이모지 번호", example = "1")
    private Long emojiId;

    @Schema(description = "내용", example = "공부하는중!")
    private String text;

    @Schema(description = "이미지 URL 목록", example = "http://localhost:8080/img1.jpg")
    private List<String> imageUrls;
}
