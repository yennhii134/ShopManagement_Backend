package com.edu.iuh.shop_managerment.services;

import com.edu.iuh.shop_managerment.dto.request.UserCreationRequest;
import com.edu.iuh.shop_managerment.dto.request.UserUpdateRequest;
import com.edu.iuh.shop_managerment.enums.user.UserRole;
import com.edu.iuh.shop_managerment.exception.AppException;
import com.edu.iuh.shop_managerment.exception.ErrorCode;
import com.edu.iuh.shop_managerment.mappers.UserMapper;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
    }
    public boolean existsUserByEmail(String email){
        return userRepository.existsUserByEmail(email);
    }
    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    // Mã hoá passord
    public String encodePassword (String password){
        return passwordEncoder.encode(password);
    }
    public User createUser(UserCreationRequest userRequest) {
        if(existsUserByEmail(userRequest.getEmail())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.userCreateedtoUser(userRequest);
        user.setPassword(encodePassword(userRequest.getPassword()));
        user.setUserRole(UserRole.USER);

        return userRepository.save(user);
    }

    public User updateUserProfile(String userId,UserUpdateRequest userUpdateRequest){
        User userUpdate = findById(userId);

        userMapper.updateUser(userUpdate,userUpdateRequest);

        return userRepository.save(userUpdate);
    }
}
