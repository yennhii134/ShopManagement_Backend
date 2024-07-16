package com.edu.iuh.shop_managerment.controllers;

import com.edu.iuh.shop_managerment.dto.request.AuthenticationRequest;
import com.edu.iuh.shop_managerment.dto.request.TokenRequest;
import com.edu.iuh.shop_managerment.dto.request.UserCreationRequest;
import com.edu.iuh.shop_managerment.dto.respone.AuthenticationRespone;
import com.edu.iuh.shop_managerment.dto.respone.TokenRespone;
import com.edu.iuh.shop_managerment.dto.respone.UserRespone;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.dto.respone.ApiResponse;
import com.edu.iuh.shop_managerment.services.AuthenticationService;
import com.edu.iuh.shop_managerment.services.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    UserService userService;
    UserRespone userRespone;
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRespone>> register(@RequestBody @Valid UserCreationRequest userCreationRequest){

        User userCreated = userService.createUser(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserRespone>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Register successfully")
                        .data(userRespone.getUserRespone(userCreated))
                        .build());
    }
    @PostMapping("/login")
    public ApiResponse<AuthenticationRespone> login(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationRespone authenticationRespone = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationRespone>builder()
                .status(HttpStatus.OK.value())
                .message("Login successfully")
                .data(authenticationRespone)
                .build();
    }
    @PostMapping("/introspect")
    public ApiResponse<TokenRespone> introspect(@RequestBody TokenRequest token) throws ParseException, JOSEException {

        TokenRespone introspect = authenticationService.introspect(token);
        return ApiResponse.<TokenRespone>builder()
                .status(HttpStatus.OK.value())
                .data(introspect)
                .build();
    }
    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody TokenRequest token) throws ParseException, JOSEException {
        authenticationService.logout(token);
        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Logout successfully")
                .build();
    }
}
