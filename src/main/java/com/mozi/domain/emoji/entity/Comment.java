package com.mozi.domain.emoji.entity;

import com.mozi.domain.emoji.controller.dto.request.CommentCreateRequest;
import com.mozi.domain.user.entity.User;
import com.mozi.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_emoji_id")
    private UserEmoji userEmoji;

    @Column(nullable = false)
    private String content;

    protected Comment() {}

    private boolean activated = true;

    @Builder
    public Comment(User user, UserEmoji userEmoji, String content) {
        this.user = user;
        this.userEmoji = userEmoji;
        this.content = content;
    }

    public static Comment from(User user, UserEmoji userEmoji, CommentCreateRequest request) {
        return Comment.builder()
                .user(user)
                .userEmoji(userEmoji)
                .content(request.getContent())
                .build();
    }

    public void delete() {
        this.activated = false;
    }
}