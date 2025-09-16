package com.mozi.domain.emoji.entity;

import com.mozi.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Emoji extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emojiUrl;
    private String saveEmojiPath;

    @Column(name = "is_representative")
    private boolean representative;

    protected Emoji() {
    }
}
