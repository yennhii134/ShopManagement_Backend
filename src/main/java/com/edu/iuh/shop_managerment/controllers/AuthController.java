package com.edu.iuh.shop_managerment.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import com.edu.iuh.shop_managerment.services.GoogleUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edu.iuh.shop_managerment.dto.request.AuthenticationRequest;
import com.edu.iuh.shop_managerment.dto.request.TokenRequest;
import com.edu.iuh.shop_managerment.dto.request.UserCreationRequest;
import com.edu.iuh.shop_managerment.dto.respone.ApiResponse;
import com.edu.iuh.shop_managerment.dto.respone.AuthenticationRespone;
import com.edu.iuh.shop_managerment.dto.respone.TokenRespone;
import com.edu.iuh.shop_managerment.dto.respone.UserRespone;
import com.edu.iuh.shop_managerment.services.AuthenticationService;
import com.edu.iuh.shop_managerment.services.UserService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthController {
    UserService userService;
    AuthenticationService authenticationService;
    GoogleUserInfoService googleUserInfoService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRespone>> register(@RequestBody UserCreationRequest userCreationRequest) {

        UserRespone userCreated = userService.createUser(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserRespone>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Register successfully")
                        .data(userCreated)
                        .build());
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationRespone> login(@RequestBody AuthenticationRequest authenticationRequest) throws ParseException, JOSEException {
        log.info("Login with username: {}", authenticationRequest);
        AuthenticationRespone authenticationRespone = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationRespone>builder()
                .status(HttpStatus.OK.value())
                .message("Login successfully")
                .data(authenticationRespone)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<TokenRespone> introspect(@RequestBody TokenRequest token) throws ParseException, JOSEException {
        log.info("Introspect token: {}", token.isRefresh());
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

    @PostMapping("/refresh-token")
    public ApiResponse<TokenRespone> refresh(@RequestBody TokenRequest token) throws ParseException, JOSEException {
        TokenRespone refresh = authenticationService.refreshToken(token,false);
        return ApiResponse.<TokenRespone>builder()
                .status(HttpStatus.OK.value())
                .data(refresh)
                .build();
    }

    @PostMapping("/check-email")
    public ApiResponse<Boolean> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean respone = userService.existsUserByEmail(email);
        String message = respone ? "Email is exists" : "Email is not exists";
        return ApiResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(respone)
                .build();
    }
    @PostMapping("/google-login")
    public String handleGoogleLogin(@RequestBody TokenRequest tokenRequest) {
        try {
            String userInfo = googleUserInfoService.getUserInfoFromGoogle(tokenRequest.getToken());
            return userInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error fetching user info";
    }
}
