package com.mozi.domain.user.service;

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
    public User register(String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.CONFLICT_REGISTER);
        }

        User newUser = User.create(email, password, passwordEncoder);

        return userRepository.save(newUser);
    }
}
