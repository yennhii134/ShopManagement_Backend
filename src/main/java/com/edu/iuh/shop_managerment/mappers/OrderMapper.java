package com.edu.iuh.shop_managerment.mappers;

import com.edu.iuh.shop_managerment.dto.request.OrderItemRequest;
import com.edu.iuh.shop_managerment.dto.request.OrderRequest;
import com.edu.iuh.shop_managerment.dto.respone.OrderItemRespone;
import com.edu.iuh.shop_managerment.dto.respone.OrderRespone;
import com.edu.iuh.shop_managerment.models.Order;
import com.edu.iuh.shop_managerment.models.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderRespone toOrderRespone(Order order);
    Order orderResponeToOrder(OrderRequest orderRequest);
   OrderItemRespone orderItemToOrderItemRespone(OrderItem orderItem);
    OrderItem orderItemRequestToOrderItem(OrderItemRequest orderItemRequest);
}
