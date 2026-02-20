package com.example.userapp.service.impl;

import com.example.userapp.dto.UserCreateRequest;
import com.example.userapp.dto.UserResponse;
import com.example.userapp.dto.mapper.UserMapper;
import com.example.userapp.entity.Address;
import com.example.userapp.entity.Task;
import com.example.userapp.entity.User;
import com.example.userapp.exception.ResourceNotFoundException;
import com.example.userapp.repository.TaskRepository;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.service.AddressService;
import com.example.userapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.userapp.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private  TaskRepository taskRepository;

    public UserServiceImpl(UserRepository userRepository, AddressService addressService, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAllWithAddress();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findByIdWithAddress(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        for (Task task : new ArrayList<>(user.getAssignedTasks())) {
            task.getAssignees().remove(user);
        }

        List<Task> ownedTasks = taskRepository.findAllByOwnerId(id);
        if (!ownedTasks.isEmpty()) {
            taskRepository.deleteAll(ownedTasks);
        }

        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserCreateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setGender(request.getGender());
        user.setBirthdate(request.getBirthdate());

        if (user.getAddress() != null) {
            Address addr = user.getAddress();
            addr.setHomeAddress(request.getHomeAddress());
            addr.setWorkAddress(request.getWorkAddress());
        }

        User updatedUser = userRepository.save(user);
        return UserMapper.toResponse(updatedUser);
    }
}