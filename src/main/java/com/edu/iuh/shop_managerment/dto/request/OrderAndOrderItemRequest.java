package com.edu.iuh.shop_managerment.dto.request;

import com.edu.iuh.shop_managerment.models.OrderItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderAndOrderItemRequest {
    OrderRequest orderRequest;
    List<OrderItemRequest> orderItemRequests;
}
