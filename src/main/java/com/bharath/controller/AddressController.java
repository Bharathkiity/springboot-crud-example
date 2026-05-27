package com.bharath.controller;

import com.bharath.entites.Address;
import com.bharath.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // CREATE
    @PostMapping
    public Address create(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    // READ ALL
    @GetMapping
    public List<Address> getAll() {
        return addressService.getAllAddresses();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Address getById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return "Address Deleted";
    }
}