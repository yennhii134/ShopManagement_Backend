package com.edu.iuh.shop_managerment.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edu.iuh.shop_managerment.dto.request.UserCreationRequest;
import com.edu.iuh.shop_managerment.dto.request.UserUpdateRequest;
import com.edu.iuh.shop_managerment.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userCreatedtoUser(UserCreationRequest userCreationRequest);
    //    UserRespone userToUserRespone(User user, Address address);
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
