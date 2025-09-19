package com.mozi.domain.emoji.repository;

import com.mozi.domain.emoji.entity.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {

    List<Emoji> findAllByRepresentativeTrue();
}
