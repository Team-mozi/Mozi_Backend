package com.mozi.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse <T> {

    private String responseCode;
    private String message;
    private T data;

    // 성공 응답 (data 있음)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                ResponseCode.OK.getCode(),
                ResponseCode.OK.getMessage(),
                data
        );
    }

    // 성공 응답 (data 없음)
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
                ResponseCode.OK.getCode(),
                ResponseCode.OK.getMessage(),
                null
        );
    }

    // 요청 실패 응답
    public static <T> ApiResponse<T> error(ResponseCode responseCode) {
        return new ApiResponse<>(
                responseCode.getCode(),
                responseCode.getMessage(),
                null
        );
    }
}
