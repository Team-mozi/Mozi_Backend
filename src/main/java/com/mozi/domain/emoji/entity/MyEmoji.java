package com.mozi.domain.emoji.entity;

import com.mozi.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class MyEmoji extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long emojiId;

    private String text;


    protected MyEmoji() {
    }

    @Builder
    private MyEmoji(Long userId, Long emojiId, String text) {
        this.userId = userId;
        this.emojiId = emojiId;
        this.text = text;
    }
}
