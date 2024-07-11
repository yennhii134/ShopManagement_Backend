package com.edu.iuh.shop_managerment.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor  @NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRespone {
    UserRespone userRespone;
    String token;
    boolean authentication;
}
