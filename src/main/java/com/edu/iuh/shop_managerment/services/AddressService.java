package com.edu.iuh.shop_managerment.services;

import com.edu.iuh.shop_managerment.enums.user.AddressStatus;
import com.edu.iuh.shop_managerment.exception.AppException;
import com.edu.iuh.shop_managerment.exception.ErrorCode;
import com.edu.iuh.shop_managerment.models.Address;
import com.edu.iuh.shop_managerment.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    public Optional<Address> findById(String addressId){
        return Optional.ofNullable(addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUNDED)));
    }
    public boolean existsAddressById(String addressId) {
        return addressRepository.existsAddressById(addressId);
    }
    public boolean existsAddressByIdAndUserId(String addresdId, String userId){
        return addressRepository.existsAddressByIdAndUserId(addresdId,userId);
    }
    public List<Address> findAllByUserId(String userId){
        return addressRepository.findAllByUserId(userId);
    }
    public Address findByIdAndUserId(String addressId, String userId){
        return addressRepository.findByIdAndUserId(addressId,userId);
    }
    public Address createAddress(String userId,Address address){
        List<Address> addresses = findAllByUserId(userId);

        if(addresses.size() >= 5) {
            throw new AppException(ErrorCode.ADDRESS_LIMIT_EXCEEDED);
        }
        if(addresses.isEmpty()){
            address.setStatus(AddressStatus.MAIN);
        } else if (address.getStatus() == AddressStatus.MAIN) {
            switchAddressMainToSecond(userId);
        } else {
            address.setStatus(AddressStatus.SECONDARY);
        }

        address.setUserId(userId);
        return addressRepository.save(address);
    }
//    Nếu trạng thái address cần tạo hay cập nhật là MAIN thì chuyển address MAIN trong list sang SECOONDARY trước
    public void switchAddressMainToSecond(String userId){
        List<Address> addresses = findAllByUserId(userId);
        addresses.stream()
                .filter(address -> address.getStatus().equals(AddressStatus.MAIN))
                .forEach(address -> {
                    address.setStatus(AddressStatus.SECONDARY);
                    addressRepository.save(address);
                });
    }
    public void radomDifferentAddressToMain(Address address, String userId){
            List<Address> addresses = findAllByUserId(userId);
            addresses.removeIf(a -> a.getId().equals(address.getId()));

                addresses.stream()
                        .findFirst()
                        .ifPresent(randomAddress -> {
                            randomAddress.setStatus(AddressStatus.MAIN);
                            addressRepository.save(randomAddress);
                        });
    }
    public void checkAddress(String addressId, String userId){
        if(!existsAddressByIdAndUserId(addressId,userId)){
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUNDED_BY_USER);
        }
        if(!existsAddressById(addressId)){
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUNDED);
        }
    }
    public Address updateAddress(String userId,String addressId,Address address){
        checkAddress(addressId,userId);
//          Kiểm tra xem address ban đầu là  là MAIN và address cần cập nhật là SECONDARY thì random một
//      address trong list thành MAIN
        Address addressCheck = findById(addressId).get();
        if(addressCheck.getStatus() == AddressStatus.MAIN && address.getStatus() == AddressStatus.SECONDARY){
            radomDifferentAddressToMain(addressCheck,userId);
        }
        if (address.getStatus() == AddressStatus.MAIN) {
            switchAddressMainToSecond(userId);
        }

        return Optional.ofNullable(addressId)
                .flatMap(this::findById)
                .map(addressToUpdate -> {
                    addressToUpdate.setAddress(address.getAddress() != null ? address.getAddress() : addressToUpdate.getAddress());
                    addressToUpdate.setStatus(address.getStatus() != null ? address.getStatus() : addressToUpdate.getStatus());
                    return addressRepository.save(addressToUpdate);
                })
                .orElseGet(() ->  createAddress(userId,address));
    }
    public boolean deleteAddress(String userId,String addressId) {
        checkAddress(addressId,userId);

        Address address = findById(addressId).get();
        if (address.getStatus() == AddressStatus.MAIN) {
            radomDifferentAddressToMain(address, userId);
        }
        addressRepository.delete(address);
        return true;
    }

}
