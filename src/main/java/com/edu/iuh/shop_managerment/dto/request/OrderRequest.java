package com.edu.iuh.shop_managerment.dto.request;


import com.edu.iuh.shop_managerment.enums.order.Payment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest {
    double orderTotal;
    String orderAddress;
    String orderPhone;
    Payment orderPayment;
}
