package com.mozi.global.common;

import lombok.Getter;

@Getter
public enum ResultCode {  // enum 분리
    SUCCESS("200", "요청 성공"),
    FAIL("400", "요청 실패"),
    ERROR("500", "서버 오류");

    private final String code;
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
