package com.edu.iuh.shop_managerment.mappers;

import com.edu.iuh.shop_managerment.dto.request.CartRequest;
import com.edu.iuh.shop_managerment.dto.respone.CartRespone;
import com.edu.iuh.shop_managerment.models.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartRespone cartToCartRespone(Cart cart);
    CartRequest cartToCartRequest(Cart cart);
}
