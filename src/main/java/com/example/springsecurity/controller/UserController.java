package com.example.springsecurity.controller;

import com.example.springsecurity.dto.UserResponse;
import com.example.springsecurity.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal CustomUserDetails user) {
        return new UserResponse(user.getId(), user.getUsername());
    }
}
