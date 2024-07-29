package com.edu.iuh.shop_managerment.repositories;

import com.edu.iuh.shop_managerment.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findCategoryByCategoryName(String categoryName);
    boolean existsCategoryByCategoryName(String categoryName);
}
