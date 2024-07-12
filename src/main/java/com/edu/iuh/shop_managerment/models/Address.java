package com.edu.iuh.shop_managerment.models;

import com.edu.iuh.shop_managerment.enums.user.AddressStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor @NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    String id;
    String address;
    @Enumerated(EnumType.STRING)
    AddressStatus status;
    @Column(name = "user_id")
    String userId;

}
