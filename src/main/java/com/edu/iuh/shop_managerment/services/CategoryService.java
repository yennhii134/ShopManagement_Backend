package com.edu.iuh.shop_managerment.services;

import com.edu.iuh.shop_managerment.dto.request.CategoryRequest;
import com.edu.iuh.shop_managerment.models.Category;
import com.edu.iuh.shop_managerment.repositories.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return new ArrayList<>(categoryRepository.findAll());

    }
    public Category findCategoryByCategoryName(CategoryRequest categoryRequest) {
        return categoryRepository.findCategoryByCategoryName(categoryRequest.getCategoryName());
    }
}
