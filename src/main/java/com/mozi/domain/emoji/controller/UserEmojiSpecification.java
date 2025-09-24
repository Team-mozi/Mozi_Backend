package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.request.UserEmojiCreateRequest;
import com.mozi.domain.emoji.controller.dto.response.RandomUserEmojiResponse;
import com.mozi.domain.emoji.controller.dto.response.UserEmojiDetailResponse;
import com.mozi.domain.emoji.controller.dto.response.UserEmojiResponse;
import com.mozi.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "UserEmoji", description = "유저 이모지 API")
public interface UserEmojiSpecification {

    @Operation(summary = "내 최신 이모지 조회", description = "가장 최근에 올린 나의 이모지를 조회합니다.")
    ResponseEntity<ApiResponse<UserEmojiResponse>> getLatestUserEmoji();

    @Operation(summary = "유저 이모지 상세 조회", description = "유저 이모지를 상세 조회합니다.")
    ResponseEntity<ApiResponse<UserEmojiDetailResponse>> getUserEmojiDetail(Long userEmojiId);

    @Operation(summary = "랜덤 이모지 조회", description = "")
    ResponseEntity<ApiResponse<List<RandomUserEmojiResponse>>> getRandomUserEmojis();

    @Operation(summary = "내 이모지 생성", description = "새로운 이모지를 생성합니다.")
    ResponseEntity<ApiResponse<Long>> createUserEmoji(UserEmojiCreateRequest request, List<MultipartFile> images, Long userId) throws IOException;

    @Operation(summary = "내 이모지 삭제", description = "내 이모지를 삭제합니다.")
    ResponseEntity<ApiResponse<Void>> deleteUserEmoji(Long userEmojiId);
}
