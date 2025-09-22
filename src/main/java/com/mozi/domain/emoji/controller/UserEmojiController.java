package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.request.UserEmojiCreateRequest;
import com.mozi.domain.emoji.controller.dto.response.RandomUserEmojiResponse;
import com.mozi.domain.emoji.controller.dto.response.UserEmojiDetailResponse;
import com.mozi.domain.emoji.controller.dto.response.UserEmojiResponse;
import com.mozi.domain.emoji.service.UserEmojiService;
import com.mozi.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user-emojis")
@RequiredArgsConstructor
@RestController
public class UserEmojiController implements UserEmojiSpecification {

    private final UserEmojiService userEmojiService;

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<UserEmojiResponse>> getLatestUserEmoji() {
        return ResponseEntity.ok(ApiResponse.success(new UserEmojiResponse()));
    }

    @GetMapping("/{userEmojiId}")
    public ResponseEntity<ApiResponse<UserEmojiDetailResponse>> getUserEmojiDetail(@PathVariable("userEmojiId") Long userEmojiId) {
        return ResponseEntity.ok(ApiResponse.success(new UserEmojiDetailResponse()));
    }

    @GetMapping("/random")
    public ResponseEntity<ApiResponse<List<RandomUserEmojiResponse>>> getRandomUserEmojis() {
        return ResponseEntity.ok(ApiResponse.success(List.of(new RandomUserEmojiResponse())));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createUserEmoji(@Valid @RequestBody UserEmojiCreateRequest request, Long userId) {
        Long id = userEmojiService.createUserEmoji(request, userId);
        return ResponseEntity.ok(ApiResponse.success(id));
    }

    @DeleteMapping("/{userEmojiId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserEmoji(@PathVariable("userEmojiId") Long userEmojiId) {
        return ResponseEntity.ok(ApiResponse.success());
    }
}
