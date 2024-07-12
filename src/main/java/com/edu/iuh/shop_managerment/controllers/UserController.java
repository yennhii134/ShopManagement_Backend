package com.edu.iuh.shop_managerment.controllers;

import com.edu.iuh.shop_managerment.dto.request.UserUpdateRequest;
import com.edu.iuh.shop_managerment.dto.respone.ApiReponse;
import com.edu.iuh.shop_managerment.dto.respone.UserRespone;
import com.edu.iuh.shop_managerment.mappers.UserMapper;
import com.edu.iuh.shop_managerment.models.Address;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.services.AddressService;
import com.edu.iuh.shop_managerment.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;
    AddressService addressService;
    UserRespone userRespone;

    @GetMapping("/{userId}")
    public UserRespone getUser(@PathVariable("userId") String userId){
        User user = userService.findById(userId);
        return userRespone.getUserRespone(user);
    }
    @GetMapping
    public List<UserRespone> getUsers(){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        log.info("User name: {}",authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return userService.getUsers().stream()
                .map(userRespone::getUserRespone)
                .collect(Collectors.toList());
    }

    @PutMapping("/{userId}/profile")
    public ApiReponse<UserRespone> updateUserProfile(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest userUpdateRequest){

        User userUpdate = userService.updateUserProfile(userId,userUpdateRequest);
        return ApiReponse.<UserRespone>builder()
                .status(HttpStatus.OK.value())
                .message("Updated user successfully")
                .data(userRespone.getUserRespone(userUpdate))
                .build();
    }
    @GetMapping("/{userId}/address/{addressId}")
    public Address getAddress(@PathVariable("userId") String userId,@PathVariable("addressId") String addressId){
        userService.findById(userId);
        return addressService.findByIdAndUserId(addressId,userId);
    }
    @GetMapping("/{userId}/address")
    public List<Address> getAddresses(@PathVariable("userId") String userId){
        userService.findById(userId);
        return addressService.findAllByUserId(userId);
    }
    @PostMapping("/{userId}/address")
    public ApiReponse<Address> createAddress(@PathVariable("userId") String userId, @RequestBody Address address){
        userService.findById(userId);
        Address addressCreated = addressService.createAddress(userId,address);
        return ApiReponse.<Address>builder()
                .status(HttpStatus.OK.value())
                .message("Created address successfully")
                .data(addressCreated)
                .build();
    }
    @PutMapping("/{userId}/address/{addressId}")
    public ApiReponse<Address> updateUserAddress(@PathVariable("userId") String userId,@PathVariable("addressId") String addressId,
                                           @RequestBody Address address){
        userService.findById(userId);

        Address adddressUpdated = addressService.updateAddress(userId,addressId,address);
        return ApiReponse.<Address>builder()
                .status(HttpStatus.OK.value())
                .message("Updated address successfully")
                .data(adddressUpdated)
                .build();
    }
    @DeleteMapping("/{userId}/address/{addressId}")
     public ApiReponse<?> deleteUserAddress(@PathVariable("userId") String userId,@PathVariable("addressId") String addressId){
        userService.findById(userId);

        boolean isDeleted = addressService.deleteAddress(userId,addressId);
        if(isDeleted) {
            return ApiReponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Deleted address successfully")
                    .data(null)
                    .build();
        }
        return ApiReponse.builder()
                .status(HttpStatus.OK.value())
                .message("Deleted address fail")
                .data(null)
                .build();
    }
}
