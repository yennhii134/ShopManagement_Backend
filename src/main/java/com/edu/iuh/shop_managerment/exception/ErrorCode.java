package com.edu.iuh.shop_managerment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(),"Unauthenticated"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED.value(),"Invalid password"),
    USER_EXISTED(HttpStatus.BAD_REQUEST.value() ,"User existed"),
    USER_NOT_FOUNDED(HttpStatus.NOT_FOUND.value(),"User not founded"),
    ADDRESS_NOT_FOUNDED(HttpStatus.NOT_FOUND.value(),"Address not founded"),
    ADDRESS_NOT_FOUNDED_BY_USER(HttpStatus.NOT_FOUND.value(),"Address not founded by user"),
    ADDRESS_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST.value() ,"Address limit exceeded: Maximum of 5 addresses allowed")
    ;
    private final int status;
    private final String message;


}
