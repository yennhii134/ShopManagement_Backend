package com.edu.iuh.shop_managerment.repositories;

import com.edu.iuh.shop_managerment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUserName(String userName);
    boolean existsUserByUserName(String userName);
    Optional<User> findById(String id);
}
