package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.request.UserEmojiCreateRequest;
import com.mozi.domain.emoji.controller.dto.response.*;
import com.mozi.domain.emoji.service.UserEmojiService;
import com.mozi.global.config.security.CustomUserDetails;
import com.mozi.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ApiResponse<LatestMyEmojiResponse>> getLatestUserEmoji(@AuthenticationPrincipal CustomUserDetails userDetails) {
        LatestMyEmojiResponse latestMyEmoji = userEmojiService.getLatestUserEmoji(userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(latestMyEmoji));
    }

    @GetMapping("/highlights")
    public ResponseEntity<ApiResponse<UserEmojiHighlightsResponse>> getUserEmojiHighlights() {
        return ResponseEntity.ok(ApiResponse.success(new UserEmojiHighlightsResponse()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserEmojiDetailResponse>> getUserEmojiDetail(@PathVariable("id") Long userEmojiId) {
        return ResponseEntity.ok(ApiResponse.success(new UserEmojiDetailResponse()));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Long>> createUserEmoji(@RequestPart("request") @Valid UserEmojiCreateRequest request,
                                                             @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        Long id = userEmojiService.createUserEmoji(request, userDetails.getUserId(), images);
        return ResponseEntity.ok(ApiResponse.success(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserEmoji(@PathVariable("id") Long userEmojiId,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        userEmojiService.deleteUserEmoji(userEmojiId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success());
    }
}
