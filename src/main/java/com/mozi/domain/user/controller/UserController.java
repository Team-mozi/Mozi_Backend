package com.mozi.domain.user.controller;

import com.mozi.domain.user.controller.dto.request.*;
import com.mozi.domain.user.controller.dto.response.LoginResponse;
import com.mozi.domain.user.controller.dto.response.RegisterResponse;
import com.mozi.domain.user.controller.dto.response.UserResponse;
import com.mozi.domain.user.service.UserService;
import com.mozi.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController implements UserSpecification{

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = userService.register(request);
        return ResponseEntity.ok(ApiResponse.create(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        // TODO: 로그인 로직 구현
        return ResponseEntity.ok(ApiResponse.success(new LoginResponse()));
    }

    @PostMapping("/social-login")
    public ResponseEntity<ApiResponse<LoginResponse>> socialLogin(@Valid @RequestBody SocialLoginRequest request) {
        // TODO: 소셜 로그인 로직 구현
        return  ResponseEntity.ok(ApiResponse.success(new LoginResponse()));
    }

    @PostMapping("/email-verifications/send")
    public ResponseEntity<ApiResponse<Void>> sendVerificationEmail(@Valid @RequestBody EmailVerificationRequest request) {
        // TODO: 이메일 발송 로직 구현
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/email-verifications/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmVerificationEmail(@Valid @RequestBody EmailVerificationConfirmRequest request) {
        // TODO: 이메일 인증 코드 검증 로직 구현
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/nickname")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserNickname(@Valid @RequestBody NicknameRequest request) {
        // TODO: 닉네임 설정 로직 구현
        return ResponseEntity.ok(ApiResponse.success(new UserResponse()));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        // TODO: 로그아웃 로직 구현
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@Valid @RequestBody UserDeleteRequest request) {
        // TODO: 회원 탈퇴 로직 구현
        return ResponseEntity.ok(ApiResponse.success());
    }
}
