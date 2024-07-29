package com.edu.iuh.shop_managerment.models;

import com.edu.iuh.shop_managerment.enums.product.Color;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_item_id")
    String id;
    @Column(name = "cart_id")
    String cartId;
    @Column(name = "product_name")
    String productName;
    @Column(name = "product_price")
    double productPrice;
    @Column(name = "product_quantity")
    int productQuantity;
    @Column(name = "product_total_price")
    double productTotalPrice;
    @Enumerated(EnumType.STRING)
    Color productColor;
    @Column(name = "order_id")
    String orderId;
}
