package com.edu.iuh.shop_managerment.repositories;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.iuh.shop_managerment.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByFacebookId(String facebookId);

    boolean existsUserByEmail(String email);

    @NotNull
    Optional<User> findById(@NotNull String id);
}
