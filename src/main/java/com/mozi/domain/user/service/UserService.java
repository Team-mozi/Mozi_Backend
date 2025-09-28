package com.mozi.domain.user.service;

import com.mozi.domain.user.controller.dto.request.LoginRequest;
import com.mozi.domain.user.controller.dto.request.NicknameRequest;
import com.mozi.domain.user.controller.dto.request.RegisterRequest;
import com.mozi.domain.user.controller.dto.request.UserWithdrawalRequest;
import com.mozi.domain.user.controller.dto.response.LoginResponse;
import com.mozi.domain.user.controller.dto.response.UserResponse;
import com.mozi.domain.user.entity.User;
import com.mozi.domain.user.repository.UserRepository;
import com.mozi.global.config.security.CustomUserDetails;
import com.mozi.global.exception.BusinessException;
import com.mozi.global.response.ErrorCode;
import com.mozi.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Transactional
    public Long register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.CONFLICT_REGISTER);
        }

        User savedUser = userRepository.save(request.toEntity(passwordEncoder));
        return savedUser.getId();
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
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

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        return jwtUtil.createAccessToken(email);
    }

    @Transactional
    public UserResponse updateNickname(NicknameRequest request) {
        String nickname = request.getNickname();

        if (userRepository.existsByNickname(nickname)) {
            throw new BusinessException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = customUserDetails.getUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        user.updateNickname(nickname);

        return UserResponse.from(user);
    }

    @Transactional
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = customUserDetails.getUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        user.logout();
    }

    @Transactional
    public void withdraw(UserWithdrawalRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = customUserDetails.getUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            throw new BusinessException(ErrorCode.BAD_PASSWORD);
        }

        user.withdraw();
    }
}
