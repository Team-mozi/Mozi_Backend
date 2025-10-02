package com.mozi.domain.emoji.repository;

import com.mozi.domain.emoji.entity.UserEmoji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserEmojiRepository extends JpaRepository<UserEmoji, Long> {

    Optional<UserEmoji> findByIdAndActivatedTrue(Long userEmojiId);

    List<UserEmoji> findAllByUserIdAndActivatedTrue(Long userId);

    Optional<UserEmoji> findFirstByUserIdAndActivatedTrueOrderByCreatedAtDesc(@Param("userId") Long userId);

    List<UserEmoji> findTop100ByActivatedTrueOrderByCreatedAtDesc();
}
