package com.mozi.domain.emoji.service;

import com.mozi.domain.emoji.controller.dto.request.UserEmojiCreateRequest;
import com.mozi.domain.emoji.entity.UserEmoji;
import com.mozi.domain.emoji.repository.UserEmojiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserEmojiService {

    private final UserEmojiRepository userEmojiRepository;

    @Transactional
    public Long createUserEmoji(UserEmojiCreateRequest request, Long userId) {
        UserEmoji userEmoji = request.toEntity(userId);
        userEmojiRepository.save(userEmoji);

        return userEmoji.getId();
    }
}
