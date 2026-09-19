package com.example.springsecurity.service;

import com.example.springsecurity.dto.RegisterRequest;
import com.example.springsecurity.dto.UserResponse;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.mapper.UserMapper;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }
        User savedUser = userMapper.toEntity(request);
        savedUser.setHashedPassword(passwordEncoder.encode(savedUser.getHashedPassword()));
        userRepository.save(savedUser);
        return userMapper.toResponse(savedUser);
    }

    public UserResponse login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .filter(u -> u.getHashedPassword().equals(rawPassword))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
        return userMapper.toResponse(user);
    }
}
