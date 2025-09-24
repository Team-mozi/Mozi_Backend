package com.mozi.domain.emoji.entity;

import com.mozi.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_emoji_id")
    private UserEmoji userEmoji;

    private String imageUrl;

    private String saveImagePath;

    protected Image() {
    }

    @Builder
    private Image(UserEmoji userEmoji, String imageUrl, String saveImagePath) {
        this.userEmoji = userEmoji;
        this.imageUrl = imageUrl;
        this.saveImagePath = saveImagePath;
    }
}
