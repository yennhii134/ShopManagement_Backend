package com.edu.iuh.shop_managerment.controllers;

;
import com.edu.iuh.shop_managerment.dto.request.CategoryRequest;
import com.edu.iuh.shop_managerment.models.Product;
import com.edu.iuh.shop_managerment.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;
    @GetMapping
    public Page<Product> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int limit) {
        return productService.getProducts(page, limit);
    }
    @GetMapping("/category/{categoryId}")
    public Page<Product> getProductsByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int limit,
            @PathVariable String categoryId) {
        return productService.getProductsByCategory(page, limit, categoryId);
    }
}
