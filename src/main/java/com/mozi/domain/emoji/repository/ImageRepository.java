package com.mozi.domain.emoji.repository;

import com.mozi.domain.emoji.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByUserEmojiIdAndActivatedTrue(Long userEmojiId);
}
