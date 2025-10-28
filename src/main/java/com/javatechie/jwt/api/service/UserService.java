package com.javatechie.jwt.api.service;

import com.javatechie.jwt.api.dto.UserResponse;
import com.javatechie.jwt.api.entity.User;
import com.javatechie.jwt.api.exception.NotFoundException;
import com.javatechie.jwt.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " was not found"));
        return new UserResponse(user.getId(), user.getUserName(), user.getEmail());
    }
}
