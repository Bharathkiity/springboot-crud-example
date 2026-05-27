package com.bharath.repo;

import com.bharath.entites.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // No custom query needed now
}