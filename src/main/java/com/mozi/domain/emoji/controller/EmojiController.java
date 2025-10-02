package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.response.EmojiHighlightsResponse;
import com.mozi.domain.emoji.controller.dto.response.EmojiResponse;
import com.mozi.domain.emoji.service.EmojiService;
import com.mozi.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/emojis")
@RequiredArgsConstructor
@RestController
public class EmojiController implements EmojiSpecification {

    private final EmojiService emojiService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmojiResponse>>> getEmojis() {
        List<EmojiResponse> emojis = emojiService.getEmojis();
        return ResponseEntity.ok(ApiResponse.success(emojis));
    }

    @GetMapping("/highlights")
    public ResponseEntity<ApiResponse<EmojiHighlightsResponse>> getEmojiHighlights() {
        return ResponseEntity.ok(ApiResponse.success(emojiService.getEmojiHighlights()));
    }
}
