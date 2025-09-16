package com.mozi.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {  // enum 분리

    // 공통 (성공/실패)
    OK("200", HttpStatus.OK, "정상적으로 완료되었습니다."),
    ACTIVATED("200", HttpStatus.OK, "계정이 재활성화되었습니다."),
    CREATED("201", HttpStatus.CREATED, "정상적으로 생성되었습니다."),
    BAD_REQUEST("400", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR("500", HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다."),

    // 회원 (로그인/회원가입/소셜 로그인)
    CONFLICT_REGISTER("409", HttpStatus.CONFLICT, "이미 가입된 회원입니다."),
    NOT_FOUND_MEMBER("404", HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    NICKNAME_ALREADY_EXISTS("409", HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    BAD_CREDENTIAL("401", HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀렸습니다."),
    UNAUTHORIZED("401", HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    INVALID_TOKEN("401", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("401", HttpStatus.UNAUTHORIZED, "엑세스 토큰이 만료되었습니다. 토큰을 갱신해주세요."),
    NOT_EXIST_PRE_AUTH_CREDENTIAL("401", HttpStatus.UNAUTHORIZED, "사전 인증 정보가 요청에서 발견되지 않았습니다."),
    SECURITY_INCIDENT("600", HttpStatus.OK, "비정상적인 로그인 시도가 감지되었습니다."),
    SOCIAL_LOGIN_FAILED("401", HttpStatus.UNAUTHORIZED, "소셜 로그인 인증에 실패했습니다."),
    EMAIL_VERIFICATION_FAILED("400", HttpStatus.BAD_REQUEST, "이메일 인증에 실패했습니다."),

    // 게시글 (이모지 상태 등록 + 사진 및 글 업로드)
    POST_NOT_FOUND("404", HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED("500", HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    UNSUPPORTED_FILE_TYPE("400", HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다."),

    // 댓글
    COMMENT_NOT_FOUND("404", HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    COMMENT_TOO_LONG("400", HttpStatus.BAD_REQUEST, "댓글 길이가 너무 깁니다."),
    COMMENT_EMPTY("400", HttpStatus.BAD_REQUEST, "댓글 내용이 비어있습니다."),

    // 기타
    NOT_FOUND("404", HttpStatus.NOT_FOUND, "NOT FOUND");

    private final String code;
    private final HttpStatus status;
    private final String message;

    ResponseCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
