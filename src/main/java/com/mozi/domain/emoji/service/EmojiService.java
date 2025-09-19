package com.mozi.domain.emoji.service;

import com.mozi.domain.emoji.controller.dto.response.EmojiResponse;
import com.mozi.domain.emoji.controller.dto.response.RepresentativeEmojiResponse;
import com.mozi.domain.emoji.repository.EmojiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EmojiService {

    private final EmojiRepository emojiRepository;

    public List<EmojiResponse> getEmojis() {
        return emojiRepository.findAll()
            .stream()
            .map(EmojiResponse::from)
            .toList();
    }

    public List<RepresentativeEmojiResponse> getRepresentativeEmojis() {
        return emojiRepository.findAllByRepresentativeTrue()
            .stream()
            .map(RepresentativeEmojiResponse::from)
            .toList();
    }
}
