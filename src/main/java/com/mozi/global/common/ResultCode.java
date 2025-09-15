package com.mozi.global.common;

import lombok.Getter;

@Getter
public enum ResultCode {  // enum 분리

    // 성공 응답
    SUCCESS("200", "요청 성공"),

    // 클라이언트 오류
    BAD_REQUEST("400", "잘못된 요청입니다."),
    UNAUTHORIZED("401", "인증이 필요합니다."),
    FORBIDDEN("403", "권한이 없습니다."),

    // 리소스 없음
    USER_NOT_FOUND("404", "사용자를 찾을 수 없습니다."),
    POST_NOT_FOUND("404", "게시글을 찾을 수 없습니다."),

    // 회원가입/로그인 관련
    INVALID_CREDENTIALS("401-1", "이메일 또는 비밀번호가 올바르지 않습니다."),
    EMAIL_ALREADY_EXISTS("409-1", "이미 가입된 이메일입니다."),
    NICKNAME_ALREADY_EXISTS("409-2", "이미 사용 중인 닉네임입니다."),
    SOCIAL_LOGIN_ERROR("401-2", "소셜 로그인 인증에 실패했습니다."),
    TOKEN_EXPIRED("401-3", "토큰이 만료되었습니다."),
    TOKEN_INVALID("401-4", "유효하지 않은 토큰입니다."),

    // 서버 오류
    INTERNAL_SERVER_ERROR("500", "서버 오류가 발생했습니다.");


    private final String code;
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
