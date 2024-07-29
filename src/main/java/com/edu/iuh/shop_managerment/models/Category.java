package com.edu.iuh.shop_managerment.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    String id;
    @Column(name = "category_name")
    String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

}
