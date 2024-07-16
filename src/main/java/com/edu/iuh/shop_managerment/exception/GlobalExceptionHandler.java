package com.edu.iuh.shop_managerment.exception;

import com.edu.iuh.shop_managerment.dto.respone.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handlingException(RuntimeException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException appException){
        ErrorCode errorCode = appException.getStatus();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.builder()
                        .status(errorCode.getStatus())
                        .message(appException.getMessage())
                        .data(null)
                        .build());
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException accessDeniedException){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.builder()
                        .status(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .data(null)
                        .build());
    }
    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAuthorizationDeniedException(AuthorizationDeniedException authorizationDeniedException){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.builder()
                        .status(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .data(null)
                        .build());
    }

}
