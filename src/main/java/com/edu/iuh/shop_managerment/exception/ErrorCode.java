package com.edu.iuh.shop_managerment.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred"),
    UNAUTHENTICATED(1001,HttpStatus.UNAUTHORIZED.value(), "Unauthenticated"),
    UNAUTHORIZED(1002,HttpStatus.FORBIDDEN.value(), "You do not have permission to access this resource"),
    VALUES_NOT_NULL(1003,HttpStatus.BAD_REQUEST.value(), "Values cannot be null"),
    INVALID_PASSWORD(1004,HttpStatus.BAD_REQUEST.value(), "Invalid password"),
    USER_EXISTED(1005,HttpStatus.OK.value(), "User existed"),
    USER_NOT_FOUNDED(1006,HttpStatus.NOT_FOUND.value(), "User not founded"),
    ADDRESS_NOT_FOUNDED(1007,HttpStatus.NOT_FOUND.value(), "Address not founded"),
    ADDRESS_LIMIT_EXCEEDED(1008,HttpStatus.BAD_REQUEST.value(), "Address limit exceeded: Maximum of 5 addresses allowed"),
    PRODUCT_NOT_FOUNDED(1009,HttpStatus.NOT_FOUND.value(), "Product not founded"),
    PRODUCT_DETAIL_NOT_FOUNDED(1010,HttpStatus.NOT_FOUND.value(), "Product detail not founded"),
    CART_NOT_FOUNDED(1011,HttpStatus.NOT_FOUND.value(), "Cart not founded");
    private final int code;
    private final int statusCode;
    private final String message;
}
