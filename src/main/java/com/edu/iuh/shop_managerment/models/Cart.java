package com.edu.iuh.shop_managerment.models;

import com.edu.iuh.shop_managerment.enums.product.Color;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id")
    String id;
    @Temporal(TemporalType.DATE)
    Date date;
    @Column(name = "product_id")
    String productId;
    @Column(name = "product_detail")
    String productDetail;
    @Enumerated(EnumType.STRING)
    Color productColor;
    @Column(name = "quantity")
    int quantity;
    @Column(name = "total_price")
    double totalPrice;
    @Column(name = "user_id")
    String userId;


}
