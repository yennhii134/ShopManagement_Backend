package com.edu.iuh.shop_managerment.dto.respone;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenRespone {
    String accessToken;
    String refreshToken;
    boolean validToken;
}
