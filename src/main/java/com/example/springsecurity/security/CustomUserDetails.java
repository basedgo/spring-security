package com.example.springsecurity.security;

import java.util.Collection;
import java.util.Collections;
import com.example.springsecurity.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }
    
    @Override 
    public String getUsername() {
        return user.getUsername();
    }

    @Override 
    public String getPassword() {
        return user.getHashedPassword();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
