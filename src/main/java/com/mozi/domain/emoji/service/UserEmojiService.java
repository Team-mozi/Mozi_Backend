package com.mozi.domain.emoji.service;

import com.mozi.domain.emoji.controller.dto.request.UserEmojiCreateRequest;
import com.mozi.domain.emoji.controller.dto.response.*;
import com.mozi.domain.emoji.entity.Image;
import com.mozi.domain.emoji.entity.UserEmoji;
import com.mozi.domain.emoji.repository.EmojiRepository;
import com.mozi.domain.emoji.repository.ImageRepository;
import com.mozi.domain.emoji.repository.UserEmojiRepository;
import com.mozi.domain.user.entity.User;
import com.mozi.domain.user.repository.UserRepository;
import com.mozi.global.exception.BusinessException;
import com.mozi.global.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.mozi.global.response.ErrorCode.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserEmojiService {

    private final UserEmojiRepository userEmojiRepository;
    private final ImageRepository imageRepository;
    private final FileManager fileManager;
    private final UserRepository userRepository;
    private final EmojiRepository emojiRepository;

    public LatestMyEmojiResponse getLatestUserEmoji(Long userId) {
        return userEmojiRepository.findFirstByUserIdAndActivatedTrueOrderByCreatedAtDesc(userId)
            .map(LatestMyEmojiResponse::from)
            .orElse(null);
    }

    public UserEmojiHighlightsResponse getUserEmojiHighlights(Long currentUserId) {
        List<UserEmoji> latest = userEmojiRepository.findTop100ByActivatedTrueOrderByCreatedAtDesc();

        Collections.shuffle(latest);
        List<RandomUserEmojiResponse> randomEmojis = latest.stream()
            .limit(6)
            .map(RandomUserEmojiResponse::from)
            .toList();

        List<RepresentativeEmojiResponse> representativeEmojis =
            emojiRepository.findAllByRepresentativeTrue().stream()
                .map(RepresentativeEmojiResponse::from)
                .toList();

        LatestMyEmojiResponse latestMyEmojiResponse = userEmojiRepository
            .findFirstByUserIdAndActivatedTrueOrderByCreatedAtDesc(currentUserId)
            .map(LatestMyEmojiResponse::from)
            .orElse(null);

        return UserEmojiHighlightsResponse.builder()
            .randomEmojis(randomEmojis)
            .representativeEmojis(representativeEmojis)
            .latestMyEmojiResponse(latestMyEmojiResponse)
            .build();
    }

    public UserEmojiDetailResponse getUserEmojiDetail(Long userEmojiId) {
        UserEmoji userEmoji = userEmojiRepository.findByIdAndActivatedTrue(userEmojiId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER_EMOJI));

        User user = userRepository.findById(userEmoji.getUserId())
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));

        List<String> imageUrls = imageRepository.findAllByUserEmojiIdAndActivatedTrue(userEmojiId)
            .stream()
            .map(Image::getImageUrl)
            .toList();

        return UserEmojiDetailResponse.from(userEmoji, user.getNickname(), imageUrls);
    }

    @Transactional
    public Long createUserEmoji(UserEmojiCreateRequest request, Long userId, List<MultipartFile> images) throws IOException {
        UserEmoji userEmoji = request.toEntity(userId);
        userEmojiRepository.save(userEmoji);

        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                String saveImagePath = fileManager.upload(file, "emoji");
                String imageUrl = fileManager.getUrl(saveImagePath);

                Image image = Image.builder()
                    .userEmoji(userEmoji)
                    .imageUrl(imageUrl)
                    .saveImagePath(saveImagePath)
                    .build();

                imageRepository.save(image);
            }
        }

        return userEmoji.getId();
    }

    @Transactional
    public void deleteUserEmoji(Long userEmojiId, Long userId) {
        UserEmoji userEmoji = getUserEmojiById(userEmojiId, userId);
        userEmoji.delete();
    }

    private UserEmoji getUserEmojiById(Long userEmojiId, Long userId) {
        UserEmoji userEmoji = userEmojiRepository.findByIdAndActivatedTrue(userEmojiId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER_EMOJI));

        if (!userEmoji.isOwnedBy(userId)) {
            throw new BusinessException(FORBIDDEN_USER_EMOJI);
        }

        return userEmoji;
    }
}
