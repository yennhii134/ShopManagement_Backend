package com.edu.iuh.shop_managerment.repositories;

import com.edu.iuh.shop_managerment.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductByProductName(String productName);
    boolean existsProductByProductName(String productName);
    Page<Product> findProductsByCategory(String categoryId, Pageable pageable);

}
