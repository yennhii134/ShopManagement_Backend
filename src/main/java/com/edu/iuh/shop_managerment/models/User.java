package com.edu.iuh.shop_managerment.models;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

import com.edu.iuh.shop_managerment.enums.user.Gender;
import com.edu.iuh.shop_managerment.enums.user.UserRole;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    String id;
    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String email;
    @Column(name = "pass_word")
    String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    UserRole userRole;
    @Column(name = "full_name")
    String fullName;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Temporal(TemporalType.DATE)
    Date dob;
    @Column(name = "facebook_id")
    String facebookId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userId")
    List<Address> addresses;
}
