package com.edu.iuh.shop_managerment.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.iuh.shop_managerment.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    Optional<User> findById(String id);
}
