package com.edu.iuh.shop_managerment.dto.respone;

import com.edu.iuh.shop_managerment.enums.product.Color;
import com.edu.iuh.shop_managerment.models.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartRespone {
    String id;
    String productId;
    Product product;
    int quantity;
    double totalPrice;
    Color productColor;
    String userId;
    Date date;
}
