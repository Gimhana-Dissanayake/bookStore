package com.giimhana.bookStore.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.giimhana.bookStore.dto.UserDto;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserService userService;

    public UserDetailService(PasswordEncoder passwordEncoder, UserService userService) {
        this.userService = userService;
    }

    // this is the function which is used by spring security for loading user from
    // db
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto userByEmail = userService.getUserByEmail(username);

        // this is the function which i used by spring secuity to load users from db
        return new User(userByEmail.getEmail(), userByEmail.getPassword(), new ArrayList<>());
    }

}
