package com.edu.iuh.shop_managerment.repositories;

import com.edu.iuh.shop_managerment.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
