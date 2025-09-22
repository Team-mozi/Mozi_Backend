package com.mozi.domain.user.service;

import com.mozi.domain.user.controller.dto.request.LoginRequest;
import com.mozi.domain.user.controller.dto.request.RegisterRequest;
import com.mozi.domain.user.controller.dto.response.LoginResponse;
import com.mozi.domain.user.entity.User;
import com.mozi.domain.user.repository.UserRepository;
import com.mozi.global.exception.BusinessException;
import com.mozi.global.response.ErrorCode;
import com.mozi.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.BAD_CREDENTIAL);
        }

        String accessToken = jwtUtil.createAccessToken(user.getEmail());

        return LoginResponse.from(user, accessToken);
    }
}
