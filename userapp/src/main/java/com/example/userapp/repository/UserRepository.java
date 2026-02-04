package com.example.userapp.repository;

import com.example.userapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u left join fetch u.address")
    List<User> findAllWithAddress();


    @Query("select u from User u left join fetch u.address where u.id = :id")
    Optional<User> findByIdWithAddress(Long id);

}
