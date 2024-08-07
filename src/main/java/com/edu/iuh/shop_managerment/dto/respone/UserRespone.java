package com.edu.iuh.shop_managerment.dto.respone;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.edu.iuh.shop_managerment.enums.user.Gender;
import com.edu.iuh.shop_managerment.models.Address;
import com.edu.iuh.shop_managerment.models.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRespone {
    String id;
    String email;
    String fullName;
    Gender gender;
    Date dob;
    String facebookId;
    List<Address> addresses;

    public UserRespone getUserRespone(User user) {
        return UserRespone.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .dob(user.getDob())
                .facebookId(user.getFacebookId())
                .addresses(user.getAddresses())
                .build();
    }
}
