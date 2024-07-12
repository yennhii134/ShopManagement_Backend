package com.edu.iuh.shop_managerment.models;

import com.edu.iuh.shop_managerment.enums.user.Gender;
import com.edu.iuh.shop_managerment.enums.user.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    String id;
    @Column(name = "user_name")
    String userName;
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
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "userId")
    List<Address> addresses;

}
