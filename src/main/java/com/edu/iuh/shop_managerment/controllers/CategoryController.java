package com.edu.iuh.shop_managerment.controllers;

import com.edu.iuh.shop_managerment.dto.request.CategoryRequest;
import com.edu.iuh.shop_managerment.dto.respone.ApiResponse;
import com.edu.iuh.shop_managerment.models.Category;
import com.edu.iuh.shop_managerment.services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/category")
    public ApiResponse<Category> getCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.findCategoryByCategoryName(categoryRequest);
        return new ApiResponse<>(200, "Success", categoryService.findCategoryByCategoryName(categoryRequest));
    }
}
