package com.bharath.service;

import com.bharath.entites.Address;

import java.util.List;

public interface AddressService {

    Address saveAddress(Address address);

    List<Address> getAllAddresses();

    Address getAddressById(Long id);

    void deleteAddress(Long id);
}