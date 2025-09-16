package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.response.EmojiResponse;
import com.mozi.domain.emoji.controller.dto.response.RepresentativeEmojiResponse;
import com.mozi.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/emojis")
@RestController
public class EmojiController implements EmojiSpecification {

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmojiResponse>>> getEmojis() {
        return ResponseEntity.ok(ApiResponse.success(List.of(new EmojiResponse())));
    }

    @GetMapping("/representatives")
    public ResponseEntity<ApiResponse<List<RepresentativeEmojiResponse>>> getRepresentativeEmojis() {
        return ResponseEntity.ok(ApiResponse.success(List.of(new RepresentativeEmojiResponse())));
    }
}
