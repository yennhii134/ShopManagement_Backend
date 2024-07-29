package com.edu.iuh.shop_managerment.dto.request;

import java.sql.Date;

import com.edu.iuh.shop_managerment.enums.user.Gender;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String email;
    String password;
    String fullName;
    Gender gender;
    Date dob;
}
