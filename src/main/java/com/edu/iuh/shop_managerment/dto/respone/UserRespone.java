package com.edu.iuh.shop_managerment.dto.respone;

import com.edu.iuh.shop_managerment.enums.user.Gender;
import com.edu.iuh.shop_managerment.models.Address;
import com.edu.iuh.shop_managerment.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
@Component
@AllArgsConstructor @NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRespone {
    String id;
    String email;
    String fullName;
    Gender gender;
    Date dob;
    List<Address> addresses;

    public UserRespone getUserRespone(User user) {
        return UserRespone.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .dob(user.getDob())
                .addresses(user.getAddresses())
                .build();
    }
}
