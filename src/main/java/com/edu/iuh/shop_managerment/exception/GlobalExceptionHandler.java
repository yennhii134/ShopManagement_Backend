package com.edu.iuh.shop_managerment.exception;

import com.edu.iuh.shop_managerment.dto.respone.ApiReponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handlingException(RuntimeException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiReponse> handlingAppException(AppException appException){
        ErrorCode errorCode = appException.getStatus();
        return ResponseEntity.status(errorCode.getStatus()).body(new ApiReponse<>(errorCode.getStatus(),errorCode.getMessage(),null));
    }
}
