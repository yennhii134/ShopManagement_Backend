package com.edu.iuh.shop_managerment.dto.request;

import com.edu.iuh.shop_managerment.enums.product.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderItemRequest {
    String cartId;
    String productName;
    double productPrice;
    int productQuantity;
    double productTotalPrice;
    Color productColor;
}
