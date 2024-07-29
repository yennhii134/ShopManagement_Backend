package com.edu.iuh.shop_managerment.services;

import com.edu.iuh.shop_managerment.dto.request.CategoryRequest;
import com.edu.iuh.shop_managerment.models.Product;
import com.edu.iuh.shop_managerment.repositories.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;

    public Page<Product> getProducts(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findAll(pageable);
    }

   public Page<Product> getProductsByCategory(int page, int limit, String categoryId) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findProductsByCategory(categoryId, pageable);
    }
}
