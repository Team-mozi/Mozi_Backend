package com.mozi.domain.user.service;

import com.mozi.domain.emoji.entity.UserEmoji;
import com.mozi.domain.emoji.repository.UserEmojiRepository;
import com.mozi.domain.user.controller.dto.request.*;
import com.mozi.domain.user.controller.dto.response.LoginResponse;
import com.mozi.domain.user.controller.dto.response.NicknameExistsResponse;
import com.mozi.domain.user.controller.dto.response.UserResponse;
import com.mozi.domain.user.entity.User;
import com.mozi.domain.user.repository.UserRepository;
import com.mozi.global.email.MailSendService;
import com.mozi.global.entity.BaseEntity;
import com.mozi.global.exception.BusinessException;
import com.mozi.global.redis.RedisService;
import com.mozi.global.response.ErrorCode;
import com.mozi.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserEmojiRepository userEmojiRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final MailSendService mailSendService;

    private final RedisService redisService;

    private static final String AUTH_CODE_PREFIX = "AuthCode_";

    @Transactional
    public Long register(RegisterRequest request) {

        if (userRepository.existsByEmailAndActivatedTrue(request.getEmail())) {
            throw new BusinessException(ErrorCode.CONFLICT_REGISTER);
        }

        User savedUser = userRepository.save(request.toEntity(passwordEncoder));
        return savedUser.getId();
    }

    @Transactional
    public void sendVerificationEmail(EmailVerificationRequest request) {
        if (userRepository.existsByEmailAndActivatedTrue(request.getEmail())) {
            throw new BusinessException(ErrorCode.CONFLICT_REGISTER);
        }

        String title = "Mozi 회원가입 인증 이메일 입니다.";
        mailSendService.sendAuthEmail(request.getEmail(), title, "mail/verificationCode");
    }

    @Transactional
    public void verifyEmail(EmailVerificationConfirmRequest request) {
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + request.getEmail());

        if (redisAuthCode == null || !redisAuthCode.equals(request.getVerificationCode())) {
            throw new BusinessException(ErrorCode.EMAIL_VERIFICATION_FAILED);
        }

        redisService.deleteValues(AUTH_CODE_PREFIX + request.getEmail());
    }

    @Transactional
    public void sendPasswordResetEmail(EmailVerificationRequest request) {
        if (!userRepository.existsByEmailAndActivatedTrue(request.getEmail())) {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }

        String title = "Mozi 비밀번호 재설정 인증 이메일 입니다.";
        mailSendService.sendAuthEmail(request.getEmail(), title, "mail/passwordResetCode");
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndActivatedTrue(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.BAD_CREDENTIAL);
        }

        String accessToken = jwtUtil.createAccessToken(user.getEmail());
        String refreshToken = jwtUtil.createRefreshToken(user.getEmail());

        user.updateRefreshToken(refreshToken);

        return LoginResponse.from(user, accessToken, refreshToken);
    }

    @Transactional
    public String reissueAccessToken(String refreshToken) {

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        String email = jwtUtil.getEmail(refreshToken);

        User user = userRepository.findByEmailAndActivatedTrue(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        return jwtUtil.createAccessToken(email);
    }

    @Transactional(readOnly = true)
    public NicknameExistsResponse checkNicknameDuplicate(String nickname) {
        boolean exists = userRepository.existsByNicknameAndActivatedTrue(nickname);
        return NicknameExistsResponse.of(exists);
    }

    @Transactional
    public UserResponse updateNickname(NicknameRequest request, Long currentUserId) {
        String newNickname = request.getNickname();

        if (userRepository.existsByNicknameAndActivatedTrue(newNickname)) {
            throw new BusinessException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        User user = getActivatedUserById(currentUserId);
        user.updateNickname(newNickname);

        return UserResponse.from(user);
    }

    @Transactional
    public void logout(Long currentUserId) {
        User user = getActivatedUserById(currentUserId);
        user.logout();
    }

    @Transactional
    public void withdraw(UserWithdrawalRequest request, Long currentUserId) {
        User user = getActivatedUserById(currentUserId);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.BAD_PASSWORD);
        }
        user.withdraw();

        List<UserEmoji> userEmojis = userEmojiRepository.findAllByUserIdAndActivatedTrue(currentUserId);
        userEmojis.forEach(BaseEntity::unActivated);
    }

    private User getActivatedUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }
}
