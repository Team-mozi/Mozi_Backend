package com.mozi.domain.user.controller;

import com.mozi.domain.user.controller.dto.request.*;
import com.mozi.domain.user.controller.dto.response.LoginResponse;
import com.mozi.domain.user.controller.dto.response.NicknameExistsResponse;
import com.mozi.domain.user.controller.dto.response.UserResponse;
import com.mozi.global.config.security.CustomUserDetails;
import com.mozi.global.config.swagger.ApiErrorCodeExamples;
import com.mozi.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import static com.mozi.global.response.ErrorCode.*;

@Tag(name = "User", description = "인증 / 회원 API")
public interface UserSpecification {

    @SecurityRequirements({})
    @ApiErrorCodeExamples({CONFLICT_REGISTER, EMAIL_NOT_VERIFIED})
    @Operation(summary = "회원 가입", description = "회원 정보를 등록합니다.")
    ResponseEntity<ApiResponse<Long>> register(RegisterRequest request);

    @SecurityRequirements({})
    @ApiErrorCodeExamples({NOT_FOUND_MEMBER, BAD_CREDENTIAL})
    @Operation(summary = "자체 로그인", description = "이메일과 비밀번호로 로그인을 수행합니다.")
    ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request);

    @SecurityRequirements({})
    @ApiErrorCodeExamples({INVALID_TOKEN, NOT_FOUND_MEMBER})
    @Operation(summary = "액세스 토큰 재발급", description = "유효한 리프레시 토큰을 통해 새로운 액세스 토큰을 발급받습니다.")
    ResponseEntity<ApiResponse<String>> reissue(TokenRefreshRequest request);

    @Operation(summary = "소셜 로그인", description = "카카오/애플 소셜 계정을 이용해 로그인을 수행합니다.")
    ResponseEntity<ApiResponse<LoginResponse>> socialLogin(SocialLoginRequest request);

    @SecurityRequirements({})
    @ApiErrorCodeExamples({CONFLICT_REGISTER})
    @Operation(summary = "이메일 인증 코드 발송", description = "회원가입을 위해 이메일로 인증 코드를 발송합니다.")
    ResponseEntity<ApiResponse<Void>> sendVerificationEmail(EmailVerificationRequest request);

    @SecurityRequirements({})
    @ApiErrorCodeExamples({EMAIL_VERIFICATION_FAILED})
    @Operation(summary = "이메일 인증 코드 확인", description = "이메일로 발송된 인증 코드를 확인하여 인증을 완료합니다.")
    ResponseEntity<ApiResponse<Void>> confirmVerificationEmail(EmailVerificationConfirmRequest request);

    @SecurityRequirements({})
    @ApiErrorCodeExamples({NOT_FOUND_MEMBER})
    @Operation(summary = "비밀번호 재설정 이메일 발송", description = "비밀번호 재설정을 위해 이메일로 인증 코드를 발송합니다.")
    ResponseEntity<ApiResponse<Void>> sendPasswordResetEmail(EmailVerificationRequest request);

    @SecurityRequirements({})
    @ApiErrorCodeExamples({EMAIL_VERIFICATION_FAILED})
    @Operation(summary = "비밀번호 재설정 이메일 인증 코드 확인", description = "비밀번호 재설정을 위해 이메일로 발송된 인증 코드를 확인합니다.")
    ResponseEntity<ApiResponse<Void>> verifyPasswordResetEmail(EmailVerificationConfirmRequest request);

    @SecurityRequirements({})
    @ApiErrorCodeExamples({EMAIL_NOT_VERIFIED, NOT_FOUND_MEMBER})
    @Operation(summary = "비밀번호 재설정", description = "이메일 인증 완료 후, 새로운 비밀번호로 재설정합니다.")
    ResponseEntity<ApiResponse<Void>> resetPassword(PasswordResetRequest request);

    @Operation(summary = "닉네임 중복 확인", description = "입력한 닉네임이 사용 가능한지 확인합니다.")
    ResponseEntity<ApiResponse<NicknameExistsResponse>> checkNicknameDuplicate(@RequestParam("nickname") String nickname);

    @ApiErrorCodeExamples({NOT_FOUND_MEMBER, NICKNAME_ALREADY_EXISTS})
    @Operation(summary = "닉네임 설정", description = "로그인한 사용자의 닉네임을 설정합니다. (Access Token 필요)")
    ResponseEntity<ApiResponse<UserResponse>> updateUserNickname(NicknameRequest request, CustomUserDetails userDetails);

    @ApiErrorCodeExamples({NOT_FOUND_MEMBER})
    @Operation(summary = "로그아웃", description = "사용자의 Refresh Token을 삭제하여 로그아웃 처리합니다. (Access Token 필요)\n" +
            "\n클라이언트에서는 Access Token과 Refresh Token을 모두 삭제해야 합니다.")
    ResponseEntity<ApiResponse<Void>> logout(CustomUserDetails userDetails);

    @ApiErrorCodeExamples({NOT_FOUND_MEMBER, BAD_PASSWORD})
    @Operation(summary = "회원 탈퇴", description = "현재 로그인된 사용자의 계정을 삭제합니다. (Access Token 필요)")
    ResponseEntity<ApiResponse<Void>> withdraw(UserWithdrawalRequest request, CustomUserDetails userDetails);

}
