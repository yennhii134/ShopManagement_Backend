package com.edu.iuh.shop_managerment.services;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    AddressService addressService;
    UserRespone userRespone;

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
    }
    public boolean existsUserByUserName(String userName){
        return userRepository.existsUserByUserName(userName);
    }
    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User getCurrentUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
       return findById(userId);
    }
    public UserRespone getMyInfo(){
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
    private String encodePassword (String password){
        return passwordEncoder.encode(password);
    }
    public User createUser(UserCreationRequest userRequest) {
        if(existsUserByUserName(userRequest.getUserName())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.userCreatedtoUser(userRequest);
        user.setPassword(encodePassword(userRequest.getPassword()));
        user.setUserRole(UserRole.USER);

        return userRepository.save(user);
    }

    public User updateUserProfile(UserUpdateRequest userUpdateRequest){
        User userUpdate = getCurrentUser();

        userMapper.updateUser(userUpdate,userUpdateRequest);

        return userRepository.save(userUpdate);
    }
}
