package com.mozi.domain.user.service;

import com.mozi.domain.user.entity.User;
import com.mozi.domain.user.repository.UserRepository;
import com.mozi.global.exception.BusinessException;
import com.mozi.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User register(String email, String password) {
        User user = User.builder()
                .email(email)
                .password(password)
                .build();
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ErrorCode.CONFLICT_REGISTER);
        }
    }
}
