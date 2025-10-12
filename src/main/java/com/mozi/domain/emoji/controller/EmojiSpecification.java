package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.response.EmojiHighlightsResponse;
import com.mozi.domain.emoji.controller.dto.response.EmojiResponse;
import com.mozi.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Emoji", description = "이모지 API")
public interface EmojiSpecification {

    @Operation(summary = "이모지 목록 조회", description = "이모지 목록을 조회합니다.")
    ResponseEntity<ApiResponse<List<EmojiResponse>>> getEmojis();

    @SecurityRequirements({})
    @Operation(summary = "비회원 메인 화면 조회", description = "대표 이모지 목록과 랜덤 이모지 목록을 조회합니다.")
    ResponseEntity<ApiResponse<EmojiHighlightsResponse>> getEmojiHighlights();
}
