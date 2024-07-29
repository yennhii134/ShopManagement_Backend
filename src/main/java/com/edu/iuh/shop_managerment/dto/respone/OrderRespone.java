package com.edu.iuh.shop_managerment.dto.respone;

import com.edu.iuh.shop_managerment.enums.order.Payment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRespone {
    String id;
    Date orderDate;
    String orderStatus;
    double orderTotal;
    String orderAddress;
    String orderPhone;
    Payment orderPayment;
}
