package com.mozi.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 회원
    CONFLICT_REGISTER("U001",HttpStatus.CONFLICT, "이미 가입된 회원입니다."),
    NOT_FOUND_MEMBER("U002", HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    NICKNAME_ALREADY_EXISTS("U003", HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    BAD_CREDENTIAL("U004", HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀렸습니다."),
    INVALID_TOKEN("U005", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("U006", HttpStatus.UNAUTHORIZED, "엑세스 토큰이 만료되었습니다. 토큰을 갱신해주세요."),
    NOT_EXIST_PRE_AUTH_CREDENTIAL("U007", HttpStatus.UNAUTHORIZED, "사전 인증 정보가 요청에서 발견되지 않았습니다."),
    SECURITY_INCIDENT("U008", HttpStatus.OK, "비정상적인 로그인 시도가 감지되었습니다."),
    SOCIAL_LOGIN_FAILED("U009", HttpStatus.UNAUTHORIZED, "소셜 로그인 인증에 실패했습니다."),
    EMAIL_VERIFICATION_FAILED("U010", HttpStatus.BAD_REQUEST, "이메일 인증에 실패했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
