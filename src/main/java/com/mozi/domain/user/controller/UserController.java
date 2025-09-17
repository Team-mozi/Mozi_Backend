package com.mozi.domain.user.controller;

import com.mozi.domain.user.controller.dto.request.*;
import com.mozi.domain.user.controller.dto.response.LoginResponse;
import com.mozi.domain.user.controller.dto.response.RegisterResponse;
import com.mozi.domain.user.controller.dto.response.UserResponse;
import com.mozi.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController implements UserSpecification{

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
        // TODO: 회원가입 로직 구현
        return ResponseEntity.ok(ApiResponse.success(new RegisterResponse()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        // TODO: 로그인 로직 구현
        return ResponseEntity.ok(ApiResponse.success(new LoginResponse()));
    }

    @PostMapping("/social-login")
    public ResponseEntity<ApiResponse<LoginResponse>> socialLogin(@RequestBody SocialLoginRequest request) {
        // TODO: 소셜 로그인 로직 구현
        return  ResponseEntity.ok(ApiResponse.success(new LoginResponse()));
    }

    @PostMapping("/email-verifications/send")
    public ResponseEntity<ApiResponse<Void>> sendVerificationEmail(@RequestBody EmailVerificationRequest request) {
        // TODO: 이메일 발송 로직 구현
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/email-verifications/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmVerificationEmail(@RequestBody EmailVerificationConfirmRequest request) {
        // TODO: 이메일 인증 코드 검증 로직 구현
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/nickname")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserNickname(@RequestBody NicknameRequest request) {
        // TODO: 닉네임 설정 로직 구현
        return ResponseEntity.ok(ApiResponse.success(new UserResponse()));
    }
}
