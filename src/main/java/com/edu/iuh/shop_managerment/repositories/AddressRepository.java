package com.edu.iuh.shop_managerment.repositories;

import com.edu.iuh.shop_managerment.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,String> {
    Optional<Address> findById (String addressId);
    boolean existsAddressById(String addressId);
    boolean existsAddressByIdAndUserId(String addressId,String userId);
    List<Address> findAllByUserId (String userId);
    Address findByIdAndUserId(String addressId, String userId);

}
