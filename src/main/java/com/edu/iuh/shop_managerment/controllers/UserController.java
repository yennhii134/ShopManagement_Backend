package com.edu.iuh.shop_managerment.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.edu.iuh.shop_managerment.dto.request.UserUpdateRequest;
import com.edu.iuh.shop_managerment.dto.respone.ApiResponse;
import com.edu.iuh.shop_managerment.dto.respone.UserRespone;
import com.edu.iuh.shop_managerment.models.Address;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.services.AddressService;
import com.edu.iuh.shop_managerment.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    AddressService addressService;
    UserRespone userRespone;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserRespone> getUsers() {
        return userService.getUsers().stream().map(userRespone::getUserRespone).collect(Collectors.toList());
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserRespone> getMyInfo() {
        return ApiResponse.<UserRespone>builder()
                .code(HttpStatus.OK.value())
                .message("Get my info successfully")
                .data(userService.getMyInfo())
                .build();
    }

    @PutMapping("/profile")
    public ApiResponse<UserRespone> updateUserProfile(@RequestBody UserUpdateRequest userUpdateRequest) {

        User userUpdate = userService.updateUserProfile(userUpdateRequest);
        return ApiResponse.<UserRespone>builder()
                .code(HttpStatus.OK.value())
                .message("Update profile successfully")
                .data(userRespone.getUserRespone(userUpdate))
                .build();
    }

    @GetMapping("/address/{addressId}")
    public Address getAddress(@PathVariable("addressId") String addressId) {
        User user = userService.getCurrentUser();
        return addressService.findByIdAndUserId(addressId, user.getId());
    }

    @GetMapping("/addresses")
    public ApiResponse<List<Address>> getAddresses() {
        User user = userService.getCurrentUser();
        List<Address> addresses = addressService.findAllByUserId(user.getId());
        String message = addresses.isEmpty() ? "Address empty" : "Get addresses successfully";
        return ApiResponse.<List<Address>>builder()
                .code(HttpStatus.OK.value())
                .message(message)
                .data(addresses)
                .build();
    }

    @PostMapping("/address")
    public ApiResponse<Address> createAddress(@RequestBody Address address) {
        User user = userService.getCurrentUser();
        Address addressCreated = addressService.createAddress(user.getId(), address);
        return ApiResponse.<Address>builder()
                .code(HttpStatus.OK.value())
                .message("Create address successfully")
                .data(addressCreated)
                .build();
    }

    @PutMapping("/address/{addressId}")
    public ApiResponse<Address> updateUserAddress(
            @PathVariable("addressId") String addressId, @RequestBody Address address) {
        User user = userService.getCurrentUser();

        Address adddressUpdated = addressService.updateAddress(user.getId(), addressId, address);
        return ApiResponse.<Address>builder()
                .code(HttpStatus.OK.value())
                .message("Update address successfully")
                .data(adddressUpdated)
                .build();
    }

    @DeleteMapping("/address/{addressId}")
    public ApiResponse<?> deleteUserAddress(@PathVariable("addressId") String addressId) {
        User user = userService.getCurrentUser();

        boolean isDeleted = addressService.deleteAddress(user.getId(), addressId);
        String message = isDeleted ? "Deleted address successfully" : "Deleted address fail";
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message(message)
                .data(null)
                .build();
    }
}
