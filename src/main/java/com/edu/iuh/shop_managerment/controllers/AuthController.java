package com.edu.iuh.shop_managerment.controllers;

import com.edu.iuh.shop_managerment.dto.request.AuthenticationRequest;
import com.edu.iuh.shop_managerment.dto.request.UserCreationRequest;
import com.edu.iuh.shop_managerment.dto.respone.AuthenticationRespone;
import com.edu.iuh.shop_managerment.dto.respone.UserRespone;
import com.edu.iuh.shop_managerment.mappers.UserMapper;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.dto.respone.ApiReponse;
import com.edu.iuh.shop_managerment.services.AuthenticationService;
import com.edu.iuh.shop_managerment.services.UserService;
import com.nimbusds.jose.JOSEException;
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
    public ResponseEntity<ApiReponse<UserRespone>> register(@RequestBody UserCreationRequest userCreationRequest){

        User userCreated = userService.createUser(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiReponse<>(HttpStatus.CREATED.value(),"Registered successfully",
                        userRespone.getUserRespone(userCreated)));
    }
    @PostMapping("/login")
    public ApiReponse<AuthenticationRespone> login(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationRespone authenticationRespone = authenticationService.authenticate(authenticationRequest);
        return new ApiReponse<>(HttpStatus.OK.value(),"Login successfully",
                        authenticationRespone);
    }
    @PostMapping("/introspect")
    public ApiReponse<?> introspect(@RequestBody String token) throws ParseException, JOSEException {

        boolean introspect = authenticationService.introspect(token);
        if(introspect) {
            return new ApiReponse<>(HttpStatus.OK.value(),"Token valid",
                    introspect);
        }
        return new ApiReponse<>(HttpStatus.OK.value(),"Token invalid",
                null);

    }
}
