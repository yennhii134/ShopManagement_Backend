package com.edu.iuh.shop_managerment.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edu.iuh.shop_managerment.dto.respone.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    ResponseEntity<ApiResponse<?>> initResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException exception) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        return initResponse(errorCode);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException appException) {
        ErrorCode errorCode = appException.getCode();
        return initResponse(errorCode);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException accessDeniedException) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return initResponse(errorCode);
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAuthorizationDeniedException(AuthorizationDeniedException authorizationDeniedException) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return initResponse(errorCode);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    ResponseEntity<ApiResponse<?>> handlingHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        ErrorCode errorCode = ErrorCode.VALUES_NOT_NULL;
        return initResponse(errorCode);
    }

}
