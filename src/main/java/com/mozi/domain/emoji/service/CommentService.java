package com.mozi.domain.emoji.service;

import com.mozi.domain.emoji.controller.dto.request.CommentCreateRequest;
import com.mozi.domain.emoji.controller.dto.response.CommentResponse;
import com.mozi.domain.emoji.entity.Comment;
import com.mozi.domain.emoji.entity.UserEmoji;
import com.mozi.domain.emoji.repository.CommentRepository;
import com.mozi.domain.emoji.repository.UserEmojiRepository;
import com.mozi.domain.user.entity.User;
import com.mozi.domain.user.repository.UserRepository;
import com.mozi.global.exception.BusinessException;
import com.mozi.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserEmojiRepository userEmojiRepository;

    @Transactional
    public void createComment(Long userEmojiId, Long userId, CommentCreateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));


        UserEmoji userEmoji = userEmojiRepository.findByIdAndActivatedTrue(userEmojiId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EMOJI));

        Comment comment = Comment.from(user, userEmoji, request);
        commentRepository.save(comment);
    }

    public List<CommentResponse> getComments(Long userEmojiId) {
        return commentRepository.findAllByUserEmojiIdAndActivatedTrueOrderByCreatedAtAsc(userEmojiId)
                .stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_COMMENT_DELETE);
        }

        comment.delete();
    }
}
