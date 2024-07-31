package com.edu.iuh.shop_managerment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    private ErrorCode code;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode;
    }
}
