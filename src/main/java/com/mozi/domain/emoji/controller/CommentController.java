package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.request.CommentCreateRequest;
import com.mozi.domain.emoji.controller.dto.response.CommentResponse;
import com.mozi.domain.emoji.service.CommentService;
import com.mozi.global.config.security.CustomUserDetails;
import com.mozi.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController implements CommentSpecification{

    private final CommentService commentService;

    @PostMapping("/user-emojis/{userEmojiId}/comments")
    public ResponseEntity<ApiResponse<Void>> createComment(
            @PathVariable Long userEmojiId,
            @RequestBody CommentCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentService.createComment(userEmojiId, userDetails.getUserId(), request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/user-emojis/{userEmojiId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable Long userEmojiId) {

        List<CommentResponse> comments = commentService.getComments(userEmojiId);
        return ResponseEntity.ok(ApiResponse.success(comments));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentService.deleteComment(commentId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success());
    }
}