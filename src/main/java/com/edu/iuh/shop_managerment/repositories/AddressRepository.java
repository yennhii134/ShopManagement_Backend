package com.edu.iuh.shop_managerment.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.iuh.shop_managerment.enums.user.AddressStatus;
import com.edu.iuh.shop_managerment.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findById(String addressId);

    boolean existsAddressById(String addressId);

    boolean existsAddressByIdAndUserId(String addressId, String userId);

    List<Address> findAllByUserId(String userId);

    Address findByIdAndUserId(String addressId, String userId);

    Optional<Address> findByStatusAndUserId(AddressStatus status, String userId);
}
