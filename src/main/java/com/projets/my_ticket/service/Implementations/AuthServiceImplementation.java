package com.projets.my_ticket.service.Implementations;


import com.projets.my_ticket.domain.User;

import com.projets.my_ticket.exception.CustomValidationException;
import com.projets.my_ticket.service.AuthService;
import com.projets.my_ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserService userService;

    @Override
    public Optional<User> login(String email, String password) {
        return Optional.ofNullable(userService.login(email, password)
                .orElseThrow(() -> new CustomValidationException("Invalid credentials")));
    }

    @Override
    public void register(User user) {
        userService.create(user);
    }
}
