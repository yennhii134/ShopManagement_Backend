package com.edu.iuh.shop_managerment.controllers;

import com.edu.iuh.shop_managerment.dto.request.CartRequest;
import com.edu.iuh.shop_managerment.dto.respone.ApiResponse;
import com.edu.iuh.shop_managerment.dto.respone.CartRespone;
import com.edu.iuh.shop_managerment.services.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @GetMapping
    public List<CartRespone> getCarts() {
        return cartService.getCarts();
    }
    @PostMapping
    public ApiResponse<CartRespone> addProductToCart(@RequestBody  CartRequest cartRequest) {
        CartRespone cartRespone = cartService.addProductToCart(cartRequest);
        return ApiResponse.<CartRespone>builder()
                .status(200)
                .message("Add product to cart successfully")
                .data(cartRespone)
                .build();
    }
    @PutMapping
    public ApiResponse<CartRespone> updateCart(@RequestBody CartRequest cartRequest) {
        CartRespone cartRespone = cartService.updateCart(cartRequest);
        return ApiResponse.<CartRespone>builder()
                .status(200)
                .message("Update cart successfully")
                .data(cartRespone)
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCart(@PathVariable String id) {
        cartService.deleteCart(id);
        return ApiResponse.<String>builder()
                .status(200)
                .message("Delete cart successfully")
                .build();
    }
}
