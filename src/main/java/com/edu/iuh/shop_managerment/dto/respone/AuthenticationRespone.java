package com.edu.iuh.shop_managerment.dto.respone;

import org.springframework.stereotype.Component;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRespone {
    UserRespone userRespone;
    String accessToken;
    String refreshToken;
    boolean authentication;
}
