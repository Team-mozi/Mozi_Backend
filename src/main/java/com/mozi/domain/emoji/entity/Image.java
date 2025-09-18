package com.mozi.domain.emoji.entity;

import com.mozi.global.entity.BaseEntity;
import jakarta.persistence.*;
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
}
