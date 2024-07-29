package com.edu.iuh.shop_managerment.dto.respone;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderAndOrderItemRespone {
    OrderRespone orderRespone;
    List<OrderItemRespone> orderItemRespone;
}
