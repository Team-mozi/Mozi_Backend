package com.mozi.domain.emoji.repository;

import com.mozi.domain.emoji.entity.UserEmoji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserEmojiRepository extends JpaRepository<UserEmoji, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update UserEmoji ue set ue.activated = false where ue.id = :userEmojiId")
    void deactivateByUserEmojiId(Long userEmojiId);
}
