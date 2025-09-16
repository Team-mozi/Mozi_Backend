package com.mozi.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse <T> {

    private String resultCode;
    private String message;
    private T data;

    public ApiResponse(String resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
        data = null;
    }

    // 성공 응답
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    // 성공 응답 - data 없음
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    // 요청 실패 응답
    public static <T> ApiResponse<T> error(ResponseCode resultCode) {
        return new ApiResponse<>(resultCode.getCode(), resultCode.getMessage(), null);
    }
}
