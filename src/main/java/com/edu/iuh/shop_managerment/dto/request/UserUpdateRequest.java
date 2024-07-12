package com.edu.iuh.shop_managerment.dto.request;

import com.edu.iuh.shop_managerment.enums.user.Gender;
import com.edu.iuh.shop_managerment.models.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String userName;
    String fullName;
    Gender gender;
    Date dob;
}
