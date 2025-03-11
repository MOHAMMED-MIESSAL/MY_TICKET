package com.projets.my_ticket.web.controllers;

import com.projets.my_ticket.domain.User;
import com.projets.my_ticket.dto.LoginRequest;
import com.projets.my_ticket.dto.UserCreateDto;

import com.projets.my_ticket.mapper.UserMapper;
import com.projets.my_ticket.service.AuthService;
import com.projets.my_ticket.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequest loginRequest) {
        Optional<User> optionalUser = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        // Here we generate a token for the user if the credentials are valid
        if (optionalUser.isPresent()) {
            String token = jwtUtil.generateToken(optionalUser.get());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.status(200).body(response);
        }

        // If the credentials are invalid, we return a 401 Unauthorized status
        Map<String, String> response = new HashMap<>();
        response.put("message", "Invalid credentials");
        return ResponseEntity.status(401).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        authService.register(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }





}
