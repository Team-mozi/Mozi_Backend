package com.mozi.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse <T> {
    private String resultCode;
    private String msg;
    private T data;

    public ApiResponse(String resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
        data = null;
    }
}
