package com.mozi.domain.emoji.repository;

import com.mozi.domain.emoji.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserEmojiIdAndActivatedTrueOrderByCreatedAtAsc(Long userEmojiId);
}