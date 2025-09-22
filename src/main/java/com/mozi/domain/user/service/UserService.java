package com.mozi.domain.user.service;

import com.mozi.domain.user.controller.dto.request.RegisterRequest;
import com.mozi.domain.user.controller.dto.response.RegisterResponse;
import com.mozi.domain.user.entity.User;
import com.mozi.domain.user.repository.UserRepository;
import com.mozi.global.exception.BusinessException;
import com.mozi.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.CONFLICT_REGISTER);
        }

        User savedUser = userRepository.save(request.toEntity(passwordEncoder));
        return RegisterResponse.from(savedUser);
    }
}
