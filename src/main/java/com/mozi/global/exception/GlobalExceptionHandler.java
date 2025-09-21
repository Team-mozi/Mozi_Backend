package com.mozi.global.exception;

import com.mozi.global.response.ApiResponse;
import com.mozi.global.response.ErrorCode;
import com.mozi.global.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        log.warn("Validation failed: {}", errorMessage);

        ApiResponse<String> response = new ApiResponse<>(
                ResponseCode.BAD_REQUEST.getCode(),
                ResponseCode.BAD_REQUEST.getMessage(),
                errorMessage
        );

        return new ResponseEntity<>(response, ResponseCode.BAD_REQUEST.getStatus());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.error("Business exception occurred: Code - {}, Message - {}", errorCode.getCode(), errorCode.getMessage());

        ApiResponse<Void> response = ApiResponse.error(errorCode);

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
}

