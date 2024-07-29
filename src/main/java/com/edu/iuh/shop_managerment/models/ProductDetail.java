package com.edu.iuh.shop_managerment.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.edu.iuh.shop_managerment.enums.product.Color;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_detail_id")
    String id;
    @Enumerated(EnumType.STRING)
    Color productColor;
    @Column(name = "product_image")
    String productImage;
    @Column(name = "product_image_main")
    boolean productImageMain;
    @Column(name = "product_id")
    String product;

    public ProductDetail(Color productColor,String productImage,boolean productImageMain, String product) {
        this.productColor = productColor;
        this.productImage = productImage;
        this.productImageMain = productImageMain;
        this.product = product;
    }
}
