package com.mozi.domain.emoji.controller;

import com.mozi.domain.emoji.controller.dto.request.CommentCreateRequest;
import com.mozi.domain.emoji.controller.dto.response.CommentResponse;
import com.mozi.global.config.security.CustomUserDetails;
import com.mozi.global.config.swagger.ApiErrorCodeExamples;
import com.mozi.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.mozi.global.response.ErrorCode.*;

@Tag(name = "Comment", description = "댓글 API")
public interface CommentSpecification {

    @ApiErrorCodeExamples({NOT_FOUND_MEMBER, NOT_FOUND_USER_EMOJI})
    @Operation(summary = "댓글 생성", description = "특정 이모지 게시글에 댓글을 작성합니다.")
    ResponseEntity<ApiResponse<Void>> createComment(Long userEmojiId,
                                                    CommentCreateRequest request,
                                                    CustomUserDetails userDetails);

    @SecurityRequirements({})
    @Operation(summary = "댓글 목록 조회", description = "특정 이모지 게시글에 달린 모든 댓글을 조회합니다.")
    ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(Long userEmojiId);

    @ApiErrorCodeExamples({NOT_FOUND_COMMENT, FORBIDDEN_COMMENT_DELETE})
    @Operation(summary = "댓글 삭제", description = "자신이 작성한 댓글을 삭제합니다.")
    ResponseEntity<ApiResponse<Void>> deleteComment(Long commentId,
                                                    CustomUserDetails userDetails);
}