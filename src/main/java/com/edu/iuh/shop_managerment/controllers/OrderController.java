package com.edu.iuh.shop_managerment.controllers;

import com.edu.iuh.shop_managerment.dto.request.OrderAndOrderItemRequest;
import com.edu.iuh.shop_managerment.dto.respone.ApiResponse;
import com.edu.iuh.shop_managerment.dto.respone.OrderAndOrderItemRespone;
import com.edu.iuh.shop_managerment.services.OrderService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping
    public ApiResponse<Object> addOrder(@RequestBody OrderAndOrderItemRequest orderAndOrderItemRequest) throws MessagingException {
        OrderAndOrderItemRespone respone = orderService.addOrder(orderAndOrderItemRequest);
        return ApiResponse.builder()
                .status(200)
                .message("Add order successfully")
                .data(respone)
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<Object> getOrder(@PathVariable String id) {
        OrderAndOrderItemRespone respone = orderService.getOrder(id);
        return ApiResponse.builder()
                .status(200)
                .message("Get order successfully")
                .data(respone)
                .build();
    }
    @GetMapping
    public ApiResponse<Object> getAllOrder() {
        return ApiResponse.builder()
                .status(200)
                .message("Get all order successfully")
                .data(orderService.getOrders())
                .build();
    }

}
