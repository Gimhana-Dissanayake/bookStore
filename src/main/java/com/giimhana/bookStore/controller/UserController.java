package com.giimhana.bookStore.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giimhana.bookStore.config.JwtUtil;
import com.giimhana.bookStore.dto.AuthenticationRequest;
import com.giimhana.bookStore.dto.AuthenticationResponse;
import com.giimhana.bookStore.dto.UserDto;
import com.giimhana.bookStore.service.UserService;

@Validated
@RestController
@RequestMapping("api/v1")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, UserDetailsService userDetailService,
            JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {

            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Username or password is incorrect");
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse("Bearer " + token));
    }

    @PostMapping("/register")
    public ResponseEntity<UUID> addUser(@Valid @RequestBody UserDto userDto) {

        UUID uuid = userService.addUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);

    }

}
