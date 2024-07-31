package com.edu.iuh.shop_managerment.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edu.iuh.shop_managerment.dto.request.UserCreationRequest;
import com.edu.iuh.shop_managerment.dto.request.UserUpdateRequest;
import com.edu.iuh.shop_managerment.dto.respone.UserRespone;
import com.edu.iuh.shop_managerment.enums.user.AddressStatus;
import com.edu.iuh.shop_managerment.enums.user.UserRole;
import com.edu.iuh.shop_managerment.exception.AppException;
import com.edu.iuh.shop_managerment.exception.ErrorCode;
import com.edu.iuh.shop_managerment.mappers.UserMapper;
import com.edu.iuh.shop_managerment.models.Address;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    AddressService addressService;
    UserRespone userRespone;

    public User findByEmail(String userName) {
        return userRepository.findByEmail(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
    }

    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getCurrentUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return findById(userId);
    }

    public UserRespone getMyInfo() {
        User user = getCurrentUser();
        Optional<Address> address = addressService.findByStatusAndUserId(AddressStatus.MAIN, user.getId());
        if (address.isPresent()) {
            user.setAddresses(Collections.singletonList(address.get()));
        } else {
            user.setAddresses(new ArrayList<>());
        }

        return userRespone.getUserRespone(user);
    }

    // Mã hoá passord
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public UserRespone createUser(UserCreationRequest userRequest) {

        User user = userMapper.userCreatedtoUser(userRequest);
        user.setPassword(encodePassword(userRequest.getPassword()));
        user.setUserRole(UserRole.USER);

        if (user.getEmail() != null) {
            try {
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }
        } else {
            userRepository.save(user);
        }
        return userRespone.getUserRespone(user);
}

public User updateUserProfile(UserUpdateRequest userUpdateRequest) {
    User userUpdate = getCurrentUser();

    userMapper.updateUser(userUpdate, userUpdateRequest);

    return userRepository.save(userUpdate);
}

}
