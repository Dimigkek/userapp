package com.example.userapp.dto.mapper;

import com.example.userapp.dto.AddressDto;
import com.example.userapp.dto.UserCreateRequest;
import com.example.userapp.dto.UserResponse;
import com.example.userapp.entity.Address;
import com.example.userapp.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(UserCreateRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setGender(request.getGender());
        user.setBirthdate(request.getBirthdate());

        if (request.getAddresses() != null) {
            List<Address> addresses = request.getAddresses()
                    .stream()
                    .map(dto -> toAddressEntity(dto, user))
                    .collect(Collectors.toList());

            user.getAddresses().addAll(addresses);
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

        if (user.getAddresses() != null) {
            List<AddressDto> addresses = user.getAddresses()
                    .stream()
                    .map(UserMapper::toAddressDto)
                    .collect(Collectors.toList());

            response.setAddresses(addresses);
        }

        return response;
    }

    private static Address toAddressEntity(AddressDto dto, User user) {
        Address address = new Address();
        address.setType(dto.getType());
        address.setAddressText(dto.getAddressText());
        address.setUser(user);
        return address;
    }

    private static AddressDto toAddressDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setType(address.getType());
        dto.setAddressText(address.getAddressText());
        return dto;
    }
}
