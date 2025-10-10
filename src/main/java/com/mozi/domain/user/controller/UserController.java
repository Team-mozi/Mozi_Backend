package com.mozi.domain.user.controller;

import com.mozi.domain.user.controller.dto.request.*;
import com.mozi.domain.user.controller.dto.response.LoginResponse;
import com.mozi.domain.user.controller.dto.response.NicknameExistsResponse;
import com.mozi.domain.user.controller.dto.response.UserResponse;
import com.mozi.domain.user.service.UserService;
import com.mozi.global.config.security.CustomUserDetails;
import com.mozi.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController implements UserSpecification{

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Long>> register(@Valid @RequestBody RegisterRequest request) {
        Long userId = userService.register(request);
        return ResponseEntity.ok(ApiResponse.success(userId));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<String>> reissue(@RequestBody TokenRefreshRequest request) {
        String newAccessToken = userService.reissueAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(newAccessToken));
    }

    @PostMapping("/social-login")
    public ResponseEntity<ApiResponse<LoginResponse>> socialLogin(@Valid @RequestBody SocialLoginRequest request) {
        // TODO: 소셜 로그인 로직 구현
        LoginResponse response = LoginResponse.builder().build();
        return  ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/email-verifications/send")
    public ResponseEntity<ApiResponse<Void>> sendVerificationEmail(@Valid @RequestBody EmailVerificationRequest request) {
        userService.sendVerificationEmail(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/email-verifications/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmVerificationEmail(@Valid @RequestBody EmailVerificationConfirmRequest request) {
        userService.verifyEmail(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/password-reset/send-email")
    public ResponseEntity<ApiResponse<Void>> sendPasswordResetEmail(@Valid @RequestBody EmailVerificationRequest request) {
        userService.sendPasswordResetEmail(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/password-reset/confirm-email")
    public ResponseEntity<ApiResponse<Void>> verifyPasswordResetEmail(@Valid @RequestBody EmailVerificationConfirmRequest request) {
        userService.verifyPasswordResetEmail(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/password-reset/reset")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody PasswordResetRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/nickname/exists")
    public ResponseEntity<ApiResponse<NicknameExistsResponse>> checkNicknameDuplicate(@RequestParam("nickname") String nickname) {
        NicknameExistsResponse response = userService.checkNicknameDuplicate(nickname);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/nickname")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserNickname(
            @Valid @RequestBody NicknameRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        UserResponse response = userService.updateNickname(request, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        userService.logout(userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> withdraw(
            @Valid @RequestBody UserWithdrawalRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        userService.withdraw(request, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success());
    }
}