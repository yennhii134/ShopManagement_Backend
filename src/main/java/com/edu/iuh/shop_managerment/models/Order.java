package com.edu.iuh.shop_managerment.models;

import com.edu.iuh.shop_managerment.enums.order.Payment;
import com.edu.iuh.shop_managerment.enums.order.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    String id;
    @OneToMany(mappedBy = "orderId")
    List<OrderItem> orderItems;
    @Column(name = "order_date")
    Date orderDate;
    @Enumerated(EnumType.STRING)
    Status orderStatus;
    @Column(name = "order_total")
    double orderTotal;
    @Column(name = "order_address")
    String orderAddress;
    @Column(name = "order_phone")
    String orderPhone;
    @Enumerated(EnumType.STRING)
    Payment orderPayment;
    @Column(name = "user_id")
    String userId;

}
