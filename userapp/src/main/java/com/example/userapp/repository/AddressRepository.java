package com.example.userapp.repository;

import com.example.userapp.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
