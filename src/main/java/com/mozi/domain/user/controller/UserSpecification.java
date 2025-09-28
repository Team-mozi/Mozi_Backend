package com.mozi.domain.user.controller;

import com.mozi.domain.user.controller.dto.request.*;
import com.mozi.domain.user.controller.dto.response.LoginResponse;
import com.mozi.domain.user.controller.dto.response.UserResponse;
import com.mozi.global.config.swagger.ApiErrorCodeExamples;
import com.mozi.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import static com.mozi.global.response.ErrorCode.*;

@Tag(name = "User", description = "인증 / 회원 API")
public interface UserSpecification {

    @ApiErrorCodeExamples({CONFLICT_REGISTER})
    @Operation(summary = "회원 가입", description = "회원 정보를 등록합니다.")
    ResponseEntity<ApiResponse<Long>> register(RegisterRequest request);

    @ApiErrorCodeExamples({NOT_FOUND_MEMBER, BAD_CREDENTIAL})
    @Operation(summary = "자체 로그인", description = "이메일과 비밀번호로 로그인을 수행합니다.")
    ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request);

    @Operation(summary = "소셜 로그인", description = "카카오/애플 소셜 계정을 이용해 로그인을 수행합니다.")
    ResponseEntity<ApiResponse<LoginResponse>> socialLogin(SocialLoginRequest request);

    @Operation(summary = "이메일 인증 코드 발송", description = "회원가입을 위해 이메일로 인증 코드를 발송합니다.")
    ResponseEntity<ApiResponse<Void>> sendVerificationEmail(EmailVerificationRequest request);

    @Operation(summary = "이메일 인증 코드 확인", description = "이메일로 발송된 인증 코드를 확인하여 인증을 완료합니다.")
    ResponseEntity<ApiResponse<Void>> confirmVerificationEmail(EmailVerificationConfirmRequest request);

    @ApiErrorCodeExamples({NOT_FOUND_MEMBER, NICKNAME_ALREADY_EXISTS})
    @Operation(summary = "닉네임 설정", description = "로그인한 사용자의 닉네임을 설정합니다. (Access Token 필요)")
    ResponseEntity<ApiResponse<UserResponse>> updateUserNickname(NicknameRequest request);

    @ApiErrorCodeExamples({NOT_FOUND_MEMBER})
    @Operation(summary = "로그아웃", description = "사용자의 Refresh Token을 삭제하여 로그아웃 처리합니다. (Access Token 필요)\n" +
            "\n클라이언트에서는 Access Token과 Refresh Token을 모두 삭제해야 합니다.")
    ResponseEntity<ApiResponse<Void>> logout();

    @Operation(summary = "회원 탈퇴", description = "현재 로그인된 사용자의 계정을 삭제합니다. (Access Token 필요)")
    ResponseEntity<ApiResponse<Void>> deleteUser(UserDeleteRequest request);

}
