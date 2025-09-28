package com.mozi.domain.emoji.service;

import com.mozi.domain.emoji.controller.dto.request.UserEmojiCreateRequest;
import com.mozi.domain.emoji.entity.Image;
import com.mozi.domain.emoji.entity.UserEmoji;
import com.mozi.domain.emoji.repository.ImageRepository;
import com.mozi.domain.emoji.repository.UserEmojiRepository;
import com.mozi.global.exception.BusinessException;
import com.mozi.global.response.ErrorCode;
import com.mozi.global.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserEmojiService {

    private final UserEmojiRepository userEmojiRepository;
    private final ImageRepository imageRepository;
    private final FileManager fileManager;

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
        UserEmoji userEmoji = getUserEmojiById(userEmojiId);
        validateOwner(userId, userEmoji);

        userEmojiRepository.deactivateByUserEmojiId(userEmojiId);
    }

    private UserEmoji getUserEmojiById(Long userEmojiId) {
        return userEmojiRepository.findById(userEmojiId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EMOJI));
    }

    private static void validateOwner(Long userId, UserEmoji userEmoji) {
        if (!userEmoji.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_USER_EMOJI);
        }
    }
}
