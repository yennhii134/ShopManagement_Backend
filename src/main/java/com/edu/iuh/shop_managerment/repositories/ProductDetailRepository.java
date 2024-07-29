package com.edu.iuh.shop_managerment.repositories;

import com.edu.iuh.shop_managerment.enums.product.Color;
import com.edu.iuh.shop_managerment.models.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, String> {
    boolean existsProductDetailByProductColor(Color productColor);
}
