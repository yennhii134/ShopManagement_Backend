package com.edu.iuh.shop_managerment.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    String id;
    @Column(name = "product_name")
    String productName;
    @Column(name = "product_price")
    double productPrice;
    @Column(name = "product_description")
    String productDescription;
    @Column(name = "product_quantity")
    long productQuantity;
    @Column(name = "category")
    String category;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    List<ProductDetail> productDetail;


    public Product(String productName, double productPrice, String productDescription, long productQuantity, String category) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.category = category;
    }
}
