package com.edu.iuh.shop_managerment.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edu.iuh.shop_managerment.dto.respone.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handlingException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException appException) {
        ErrorCode errorCode = appException.getStatus();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .status(errorCode.getStatus())
                        .message(appException.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ApiResponse<?> handlingAccessDeniedException(AccessDeniedException accessDeniedException) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ApiResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ApiResponse<?> handlingAuthorizationDeniedException(AuthorizationDeniedException authorizationDeniedException) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ApiResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .data(null)
                .build();
    }
}
