package com.edu.iuh.shop_managerment.dto.respone;

import com.edu.iuh.shop_managerment.enums.product.Color;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderItemRespone {
    String id;
    String cartId;
    String productName;
    double productPrice;
    int productQuantity;
    double productTotalPrice;
    Color productColor;
    String orderId;
}
