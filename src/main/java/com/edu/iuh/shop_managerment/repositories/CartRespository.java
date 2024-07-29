package com.edu.iuh.shop_managerment.repositories;

import com.edu.iuh.shop_managerment.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CartRespository extends JpaRepository<Cart, String> {
    boolean existsCartByProductId(String productId);
    Cart findTopByProductId(String productId);
}
