package com.mozi.domain.emoji.service;

import com.mozi.domain.emoji.controller.dto.response.EmojiHighlightsResponse;
import com.mozi.domain.emoji.controller.dto.response.EmojiResponse;
import com.mozi.domain.emoji.controller.dto.response.RandomUserEmojiResponse;
import com.mozi.domain.emoji.controller.dto.response.RepresentativeEmojiResponse;
import com.mozi.domain.emoji.entity.UserEmoji;
import com.mozi.domain.emoji.repository.EmojiRepository;
import com.mozi.domain.emoji.repository.UserEmojiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EmojiService {

    private final EmojiRepository emojiRepository;
    private final UserEmojiRepository userEmojiRepository;

    public List<EmojiResponse> getEmojis() {
        return emojiRepository.findAll()
            .stream()
            .map(EmojiResponse::from)
            .toList();
    }

    public EmojiHighlightsResponse getEmojiHighlights() {
        List<UserEmoji> latest = userEmojiRepository.findTop100ByActivatedTrueOrderByCreatedAtDesc();
        Collections.shuffle(latest);
        List<RandomUserEmojiResponse> randomEmojis = latest.stream()
            .limit(6)
            .map(RandomUserEmojiResponse::from)
            .toList();

        List<RepresentativeEmojiResponse> representativeEmojis = emojiRepository.findAllByRepresentativeTrue().stream()
            .map(RepresentativeEmojiResponse::from)
            .toList();

        return EmojiHighlightsResponse.of(randomEmojis, representativeEmojis);
    }
}
