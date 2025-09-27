package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.request.UserEmojiCreateRequest;
import com.mozi.domain.emoji.controller.dto.response.RandomUserEmojiResponse;
import com.mozi.domain.emoji.controller.dto.response.UserEmojiDetailResponse;
import com.mozi.domain.emoji.controller.dto.response.UserEmojiResponse;
import com.mozi.domain.emoji.service.UserEmojiService;
import com.mozi.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Long>> createUserEmoji(@RequestPart("request") @Valid UserEmojiCreateRequest request,
                                                             @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                             Long userId) throws IOException {
        Long id = userEmojiService.createUserEmoji(request, userId, images);
        return ResponseEntity.ok(ApiResponse.success(id));
    }

    @DeleteMapping("/{userEmojiId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserEmoji(@PathVariable("userEmojiId") Long userEmojiId) {
        return ResponseEntity.ok(ApiResponse.success());
    }
}
