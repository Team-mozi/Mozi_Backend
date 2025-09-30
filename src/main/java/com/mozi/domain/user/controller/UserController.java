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
        // TODO: 이메일 발송 로직 구현
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/email-verifications/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmVerificationEmail(@Valid @RequestBody EmailVerificationConfirmRequest request) {
        // TODO: 이메일 인증 코드 검증 로직 구현
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/nickname/exists")
    public ResponseEntity<ApiResponse<NicknameExistsResponse>> checkNicknameDuplicate(@RequestParam("nickname") String nickname) {
        boolean exists = userService.checkNicknameDuplicate(nickname);
        return ResponseEntity.ok(ApiResponse.success(new NicknameExistsResponse(exists)));
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