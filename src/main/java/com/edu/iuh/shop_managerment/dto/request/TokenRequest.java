package com.edu.iuh.shop_managerment.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TokenRequest {
    String token;
}
