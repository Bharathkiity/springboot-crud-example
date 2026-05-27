package com.bharath.serviceImp;

import com.bharath.entites.Address;
import com.bharath.repo.AddressRepository;
import com.bharath.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // CREATE
    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    // READ ALL
    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // READ BY ID
    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    // DELETE
    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}