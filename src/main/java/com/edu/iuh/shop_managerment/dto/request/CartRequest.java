package com.edu.iuh.shop_managerment.dto.request;

import com.edu.iuh.shop_managerment.enums.product.Color;
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
public class CartRequest {
    String id;
    String productId;
    String productDetail;
    int quantity;
    double totalPrice;
    Color productColor;
}
