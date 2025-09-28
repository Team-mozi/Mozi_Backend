package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.request.UserEmojiCreateRequest;
import com.mozi.domain.emoji.controller.dto.response.LatestUserEmojiResponse;
import com.mozi.domain.emoji.controller.dto.response.UserEmojiDetailResponse;
import com.mozi.domain.emoji.controller.dto.response.UserEmojiHighlightsResponse;
import com.mozi.global.config.security.CustomUserDetails;
import com.mozi.global.config.swagger.ApiErrorCodeExamples;
import com.mozi.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.mozi.global.response.ErrorCode.*;

@Tag(name = "UserEmoji", description = "유저 이모지 API")
public interface UserEmojiSpecification {

    @Operation(summary = "내 최신 이모지 조회", description = "가장 최근에 올린 나의 이모지를 조회합니다.")
    ResponseEntity<ApiResponse<LatestUserEmojiResponse>> getLatestUserEmoji();

    @Operation(summary = "메인 화면 조회", description = "대표 이모지 목록과 랜덤 이모지 목록, 내 최신 이모지를 조회합니다.")
    ResponseEntity<ApiResponse<UserEmojiHighlightsResponse>> getUserEmojiHighlights();

    @Operation(summary = "유저 이모지 상세 조회", description = "유저 이모지를 상세 조회합니다.")
    ResponseEntity<ApiResponse<UserEmojiDetailResponse>> getUserEmojiDetail(Long userEmojiId);

    @Operation(summary = "내 이모지 생성", description = "새로운 이모지를 생성합니다.")
    ResponseEntity<ApiResponse<Long>> createUserEmoji(UserEmojiCreateRequest request, List<MultipartFile> images, CustomUserDetails userDetails) throws IOException;

    @ApiErrorCodeExamples({NOT_FOUND_USER_EMOJI, FORBIDDEN_USER_EMOJI})
    @Operation(summary = "내 이모지 삭제", description = "내 이모지를 삭제합니다.")
    ResponseEntity<ApiResponse<Void>> deleteUserEmoji(Long userEmojiId, CustomUserDetails userDetails);
}
