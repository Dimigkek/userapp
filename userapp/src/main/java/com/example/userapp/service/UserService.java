package com.example.userapp.service;

import com.example.userapp.dto.UserResponse;
import com.example.userapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    List<User> findAll();

    Optional<User> findById(Long id);

    void deleteById(Long id);

    Page<UserResponse> findAll(Pageable pageable);

}
