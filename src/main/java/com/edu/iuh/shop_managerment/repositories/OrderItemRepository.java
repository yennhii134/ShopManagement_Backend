package com.edu.iuh.shop_managerment.repositories;

import com.edu.iuh.shop_managerment.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
