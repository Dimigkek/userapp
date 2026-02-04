package com.example.userapp.dto.mapper;

import com.example.userapp.dto.UserCreateRequest;
import com.example.userapp.dto.UserResponse;
import com.example.userapp.entity.Address;
import com.example.userapp.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(UserCreateRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setGender(request.getGender());
        user.setBirthdate(request.getBirthdate());

        if (request.getHomeAddress() != null || request.getWorkAddress() != null) {
            Address address = new Address();
            address.setHomeAddress(request.getHomeAddress());
            address.setWorkAddress(request.getWorkAddress());
            address.setUser(user);
            user.setAddress(address);
        }

        return user;
    }

    public static UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setSurname(user.getSurname());
        response.setGender(user.getGender());
        response.setBirthdate(user.getBirthdate());

        if (user.getAddress() != null) {
            response.setHomeAddress(user.getAddress().getHomeAddress());
            response.setWorkAddress(user.getAddress().getWorkAddress());
        }

        return response;
    }
}
