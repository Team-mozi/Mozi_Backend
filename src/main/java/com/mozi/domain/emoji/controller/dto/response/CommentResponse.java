package com.mozi.domain.emoji.controller.dto.response;

import com.mozi.domain.emoji.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long commentId;
    private final String content;
    private final Long userId;
    private final String authorNickname;
    private final LocalDateTime createdAt;

    @Builder
    public CommentResponse(Long commentId, String content, Long userId, String authorNickname, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
        this.authorNickname = authorNickname;
        this.createdAt = createdAt;
    }

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .authorNickname(comment.getUser().getNickname())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
