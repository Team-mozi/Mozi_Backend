package com.mozi.domain.emoji.repository;

import com.mozi.domain.emoji.entity.UserEmoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmojiRepository extends JpaRepository<UserEmoji, Long> {
}
