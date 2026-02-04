package com.example.userapp.service;

import com.example.userapp.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Address save(Address address);

    List<Address> findAll();

    Optional<Address> findById(Long id);

    void deleteById(Long id);
}
