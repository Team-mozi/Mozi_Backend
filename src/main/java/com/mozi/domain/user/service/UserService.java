package com.mozi.domain.user.service;

import com.mozi.domain.user.entity.User;
import com.mozi.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

        return userRepository.save(user);

    }
}
